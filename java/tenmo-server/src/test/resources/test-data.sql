TRUNCATE users, accounts, transfers, transfer_types, transfer_statuses CASCADE;

INSERT INTO users (user_id, username, password_hash)
VALUES (1, 'user1', 'passString1'),
        (2, 'user2', 'passString2'),
        (3, 'user3', 'passString3');

INSERT INTO accounts (account_id, user_id, balance)
VALUES (1, 1, 1000),
        (2, 2, 2000),
        (3, 3, 2500);

INSERT INTO transfer_statuses (transfer_status_id, transfer_status_desc)
VALUES (1, 'Pending'),
        (2, 'Approved'),
        (3, 'Rejected');

INSERT INTO transfer_types (transfer_type_id, transfer_type_desc)
VALUES (1, 'Request'),
        (2, 'Send');

--ALTER TABLE transfers ALTER COLUMN transfer_status_id DROP NOT NULL;
--ALTER TABLE transfers ALTER COLUMN transfer_type_id DROP NOT NULL;

INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, account_from,
                account_to, amount)
VALUES (1, 2, 2, 1, 2, 10),
        (2, 2, 2, 3, 2, 15),
        (3, 2, 2, 2, 1, 20);




--UPDATE transfers SET transfer_status_id = 2;
--UPDATE transfers SET transfer_type_id = 2;

--ALTER TABLE transfers ALTER COLUMN transfer_status_id SET NOT NULL;
--ALTER TABLE transfers ALTER COLUMN transfer_type_id SET NOT NULL;

--ALTER SEQUENCE transfers_transfer_id_seq RESTART WITH 10;