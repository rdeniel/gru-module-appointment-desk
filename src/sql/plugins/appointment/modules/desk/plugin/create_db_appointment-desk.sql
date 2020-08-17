

--
-- Structure for table appointment_desk_day
--

DROP TABLE IF EXISTS appointment_desk_day;
CREATE TABLE appointment_desk_day (
id_appointment_desk int AUTO_INCREMENT,
id_form int default '0' NOT NULL,
day date NOT NULL,
desk_number int default '0' NOT NULL,
start_closing time NOT NULL,
end_closing time NOT NULL,
PRIMARY KEY (id_appointment_desk)
);
