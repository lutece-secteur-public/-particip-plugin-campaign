
--
-- Data for table core_admin_right
--
DELETE FROM core_admin_right WHERE id_right = 'CAMPAIGN_MANAGEMENT';
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url, id_order ) VALUES 
('CAMPAIGN_MANAGEMENT','campaign.adminFeature.ManageCampaigns.name',1,'jsp/admin/plugins/campaign/ManageCampaigns.jsp','campaign.adminFeature.ManageCampaigns.description',0,'campaign',NULL,NULL,NULL,4);


--
-- Data for table core_user_right
--
DELETE FROM core_user_right WHERE id_right = 'CAMPAIGN_MANAGEMENT';
INSERT INTO core_user_right (id_right,id_user) VALUES ('CAMPAIGN_MANAGEMENT',1);

