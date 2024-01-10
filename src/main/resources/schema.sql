CREATE TABLE IF NOT EXISTS seats
(
    seat_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reserved_date DATE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS users
(
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    work_type VARCHAR(255),
    seat_id BIGINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_seat_id FOREIGN KEY (seat_id) REFERENCES seats(seat_id)
);

