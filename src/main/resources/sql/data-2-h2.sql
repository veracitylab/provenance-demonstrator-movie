INSERT INTO USERS(username, password, enabled) VALUES ('matthias', '$2a$10$aAxgxyRv7/tqYw0TxmxtN.HIzImqK11RKoayN39ywcpYqo3ocAMa6', 1);
INSERT INTO USERS(username, password, enabled) VALUES ('jens', '$2a$10$kVsuPSe2qVZQa6oaSLEHfuKn1ClC9k28Ltda/xm3eMatyBWzeGjIW', 1);
INSERT INTO USERS(username, password, enabled) VALUES ('sam', '$2a$10$p/EED1bMGH3f8pgBoYaqVO0doEDqI03K9FicNFmNETaf.jqNnn7NS', 1);

INSERT INTO AUTHORITIES(username, authority) VALUES ('matthias', 'ROLE_USER');
INSERT INTO AUTHORITIES(username, authority) VALUES ('jens', 'ROLE_USER');
INSERT INTO AUTHORITIES(username, authority) VALUES ('sam', 'ROLE_USER');