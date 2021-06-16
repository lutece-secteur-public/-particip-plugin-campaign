
--
-- Structure for table campaign_campaign
--

DROP TABLE IF EXISTS campaign_campaign;
CREATE TABLE campaign_campaign (
id_campaign int AUTO_INCREMENT,
code_campaign varchar(50) default '' NOT NULL,
title varchar(255) default '' NOT NULL,
description long varchar NOT NULL,
active SMALLINT NOT NULL,
PRIMARY KEY (id_campaign)
);

--
-- Structure for table campaign_phase
--

DROP TABLE IF EXISTS campaign_phase;
CREATE TABLE campaign_phase (
id_phase int AUTO_INCREMENT,
id_campaign int default '0' NOT NULL,
starting_date TIMESTAMP NOT NULL,
ending_date TIMESTAMP NOT NULL,
label varchar(255) default '' NOT NULL,
order_num int default '0' NOT NULL,
PRIMARY KEY (id_phase),
CONSTRAINT fk_campaign_phase_campaign
    FOREIGN KEY (id_campaign)
    REFERENCES campaign_campaign (id_campaign)
);

--
-- Structure for table campaign_theme
--

DROP TABLE IF EXISTS campaign_theme;
CREATE TABLE campaign_theme (
id_theme int AUTO_INCREMENT,
id_campaign int default '0' NOT NULL,
title varchar(255) default '' NOT NULL,
description long varchar NOT NULL,
active SMALLINT NOT NULL,
front_rgb varchar(255) default '' NOT NULL,
image_file int default '0',
PRIMARY KEY (id_theme),
CONSTRAINT fk_campaign_phase_theme
    FOREIGN KEY (id_campaign)
    REFERENCES campaign_campaign (id_campaign)
);

--
-- Structure for table campaign_area
--

DROP TABLE IF EXISTS campaign_area;
CREATE TABLE campaign_area (
id_area int AUTO_INCREMENT,
id_campaign int default '0' NOT NULL,
title varchar(255) default '' NOT NULL,
type varchar(50) default '',
number_votes int default '0' NOT NULL,
active SMALLINT NOT NULL,
PRIMARY KEY (id_area),
CONSTRAINT fk_campaign_phase_area
    FOREIGN KEY (id_campaign)
    REFERENCES campaign_campaign (id_campaign)
);
