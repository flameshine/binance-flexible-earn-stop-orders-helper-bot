# This script imports some test data to speed up the development/testing process

INSERT INTO account VALUES(
    1,
    132077890,
    'R1hUeWKxUmNIZThncFZpNDJKbzFndzJaSkNrRVRKR2VOdnQxbkRMbyUwMUJiUHFacM1oTUdBYVVUM342OVc7Uw==',
    'cmNURWXWSXZTZE9Da2JTZTBZTEF4VU1GVk9UQVFCWHlvZktqUG4zVU5vZEnwdFB4WWZ4dEhjY0UzdDZBNnZrSg=='
);

UPDATE account_SEQ SET next_val = 2 WHERE next_val IS NOT NULL;