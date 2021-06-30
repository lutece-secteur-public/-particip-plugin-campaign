
--
-- Structure for table campaign_campaign
--

DROP TABLE IF EXISTS campaign_campaign;
CREATE TABLE campaign_campaign (
id_campaign int AUTO_INCREMENT,
campaign_code varchar(50) NOT NULL,
title varchar(255) default '' NOT NULL,
description long varchar NOT NULL,
active SMALLINT NOT NULL,
PRIMARY KEY (id_campaign),
UNIQUE INDEX (campaign_code)
);

--
-- Structure for table campaign_phase_type
--
DROP TABLE IF EXISTS campaign_phase_types;
CREATE TABLE campaign_phase_types (
id_phase_type int(6) NOT NULL,
phase_type_code varchar(50) NOT NULL,
label varchar(255) NOT NULL,
PRIMARY KEY (id_phase_type),
UNIQUE INDEX (phase_type_code)
);
--
-- Structure for table campaign_phase
--

DROP TABLE IF EXISTS campaign_phase;
CREATE TABLE campaign_phase (
id_phase int AUTO_INCREMENT,
phase_type_code varchar(50) NOT NULL,
campaign_code varchar(50) default '' NOT NULL,
starting_date TIMESTAMP NOT NULL,
ending_date TIMESTAMP NOT NULL,
label varchar(255) default '' NOT NULL,
order_num int default '0' NOT NULL,
PRIMARY KEY (id_phase),
UNIQUE INDEX (phase_type_code,campaign_code ),
CONSTRAINT fk_campaign_phase_campaign FOREIGN KEY (campaign_code) REFERENCES campaign_campaign (campaign_code),
CONSTRAINT fk_campaign_phases_phase_type FOREIGN KEY(phase_type_code) references campaign_phase_types(phase_type_code)

);


--
-- Structure for table campaign_theme
--

DROP TABLE IF EXISTS campaign_theme;
CREATE TABLE campaign_theme (
id_theme int AUTO_INCREMENT,
campaign_code varchar(50) default '' NOT NULL,
code_theme varchar(50) default '' NOT NULL,
title varchar(255) default '' NOT NULL,
description long varchar NOT NULL,
active SMALLINT NOT NULL,
front_rgb varchar(255) default '' NOT NULL,
image_file int default '0',
PRIMARY KEY (id_theme),
CONSTRAINT fk_campaign_phase_theme
    FOREIGN KEY (campaign_code)
    REFERENCES campaign_campaign (campaign_code)
);

--
-- Structure for table campaign_area
--

DROP TABLE IF EXISTS campaign_area;
CREATE TABLE campaign_area (
id_area int AUTO_INCREMENT,
campaign_code varchar(50) default '' NOT NULL,
title varchar(255) default '' NOT NULL,
type varchar(50) default '',
number_votes int default '0' NOT NULL,
active SMALLINT NOT NULL,
PRIMARY KEY (id_area),
CONSTRAINT fk_campaign_phase_area
    FOREIGN KEY (campaign_code)
    REFERENCES campaign_campaign (campaign_code)
);
