package nz.ac.canterbury.dataprovenancedemo.recommenders;

import nz.ac.canterbury.dataprovenancedemo.AbstractMovieRecommender;
import nz.ac.canterbury.dataprovenancedemo.provenance.AlgorithmInfo;
import nz.ac.canterbury.dataprovenancedemo.provenance.ModelInfo;
import nz.ac.canterbury.dataprovenancedemo.provenance.ProvenanceData;
import weka.classifiers.rules.JRip;
import weka.classifiers.rules.Rule;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.SerializationHelper;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RuleBased extends AbstractMovieRecommender {
    // Algorithm attributes
    private static final int THRESHOLD_RATING = 3;
    private static final String MODEL_PATH = "models/jrip_1000.weka";
    private final JRip jRipClassifier;

    // Provenance information, static and runtime
    private final AlgorithmInfo algorithmInfo;
    private final List<Rule> rules;
    private final List<String> ruleDescriptions;
    private final Set<Integer> rulesUsedThisRunIdxes = new HashSet<>();

    // Tokens
    private static final List<String> ageTokens = Arrays.asList("0", "1", "2", "3", "4");
    private static final List<String> regionTokens = Arrays.asList("0", "1", "2", "3");
    private static final List<String> sexTokens = Arrays.asList("0", "1", "2");
    private static final List<String> relStatusTokens = Arrays.asList("0", "1", "2", "3");
    private static final List<String> ratingTokens = Arrays.asList("1", "2", "3", "4", "5");

    // Attributes
    private final Attribute age = new Attribute("AGE_T", ageTokens);
    private final Attribute region = new Attribute("REGION_T", regionTokens);
    private final Attribute sex = new Attribute("SEX_T", sexTokens);
    private final Attribute relStatus = new Attribute("RELATIONSHIP_T", relStatusTokens);
    private final Attribute movieId = new Attribute("MOVIE_ID");

    private final Instances instances;

    public RuleBased() throws IllegalStateException {
        try {
            InputStream modelInput = this.getClass().getClassLoader().getResourceAsStream(MODEL_PATH);
            this.jRipClassifier = (JRip) (SerializationHelper.read(modelInput));
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot create RuleBased recommender as model has failed to load");
        }

        Attribute rating = new Attribute("RATING", ratingTokens);
        final ArrayList<Attribute> attributes = new ArrayList<>(
                Arrays.asList(age, region, sex, relStatus, movieId, rating)
        );

        this.instances = new Instances("User data to movie recommender", attributes, 1);
        this.instances.setClass(rating);
        this.rules = jRipClassifier.getRuleset();

        // This is some dirty, DIRTY hacky stuff to get the rule descriptions based on the WEKA JRip.toString()
        // source code. For provenance information we assume that the position of a rule in this array is reflected
        // in the internal JRip rule list (it should be).
        String jRipDesc = jRipClassifier.toString();
        List<String> lines = Arrays.asList(jRipDesc.split("\\r?\\n"));
        this.ruleDescriptions = lines.subList(3, lines.size() - 2);

        // Generate static provenance info
        String[] steps = new String[] {
                "Select, randomly a movieID from the list of MovieIDs",
                "Apply all model rules to predict a rating for the selected movie ID",
                String.format("If the predicted rating is greater than %s, add this movie to the recommendation list", THRESHOLD_RATING),
                "Repeat steps 0 to 2 until a specified number of recommendations has been provided, or all movie IDs have been checked"
        };
        algorithmInfo = new AlgorithmInfo.AlgorithmInfoBuilder("Rule based select", steps)
                .description("This algorithm uses pre-defined rules to determine a selection of movie " +
                        "recommendations. These rules are created using a model.")
                .build();
    }

    @Override
    protected List<Integer> algorithm(Map<String, Object> features, int resultSize) {
        this.rulesUsedThisRunIdxes.clear();

        String userAgeT;
        String userRegionT;
        String userSexT;
        String userRelStatT;

        try {
            userAgeT = features.get("user_age_t").toString();
            userRegionT = features.get("user_region_t").toString();
            userSexT = features.get("user_sex_t").toString();
            userRelStatT = features.get("user_relStat_t").toString();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("User data missing features");
        }

        // Get a random selection of movies to test against
        Collections.shuffle(movieIds);

        // Begin prediction loop
        List<Integer> predictions = new ArrayList<>();
        int startIdx = 0;

        try {
            while(startIdx < movieIds.size() && predictions.size() < resultSize) {
                DenseInstance newInstance = new DenseInstance(5);
                newInstance.setValue(age, userAgeT);
                newInstance.setValue(region, userRegionT);
                newInstance.setValue(sex, userSexT);
                newInstance.setValue(relStatus, userRelStatT);
                newInstance.setValue(movieId, movieIds.get(startIdx).doubleValue());

                this.instances.clear();
                this.instances.add(newInstance);

                int predictionIdx = (int) this.jRipClassifier.classifyInstance(this.instances.instance(0));
                int predictedRating = Integer.parseInt(ratingTokens.get(predictionIdx));

                if(predictedRating > THRESHOLD_RATING) {
                    predictions.add(movieIds.get(startIdx));

                    this.rulesUsedThisRunIdxes.addAll(
                            rules.stream()
                                    .filter(rule -> rule.covers(this.instances.instance(0)))
                                    .map(rules::indexOf)
                                    .collect(Collectors.toList())
                    );
                }
                startIdx++;
            }
        } catch (Exception e) {
            // Broad exception catching is bad, however classifier.classifyInstance throws Exception
            System.out.println("Error in creating a prediction");
        }

        return predictions;
    }

    @Override
    public ProvenanceData getProvenanceData() {
        String[] modelGenSteps = new String[] {
                "Pre-process the dataset into a format that can be used by the Weka data mining software",
                "Use JRip algorithm to produce rules from the pre-processed dataset"
        };
        List<String> rulesUsedDescription = rulesUsedThisRunIdxes.stream().map(ruleDescriptions::get)
                .collect(Collectors.toList());
        String[] rls = new String[0];

        ModelInfo modelInfo = new ModelInfo.ModelInfoBuilder(modelGenSteps)
                .rules(rulesUsedDescription.toArray(rls))
                .build();

        return new ProvenanceData.ProvenanceDataBuilder()
                .dataSet(dataSetInfo)
                .algorithmInfo(algorithmInfo)
                .modelInfo(modelInfo)
                .build();
    }

    /**
     * This method "translates" rules which contain tokenized information, de-tokenizing the tokenized fields
     */
    private void translateRules() {

    }

    /**
     * This is for testing purposes only.
     * @param args
     */
    public static void main(String[] args) {
        Map<String, Object> testData = Map.of(
                "user_age_t", "0",
                "user_region_t", "1",
                "user_sex_t", "0",
                "user_relStat_t", "2"
        );
        List<Integer> movIds = Stream.iterate(0, i -> i + 1).limit(100).collect(Collectors.toList());

        RuleBased b = new RuleBased();
        b.setAvailableMovieIds(movIds);
        List<Integer> predictedIds = b.algorithm(testData, 2);
        b.getProvenanceData();

        System.out.println(predictedIds);
    }
}
