

--
-- Data for table core_admin_right
--
DELETE FROM core_admin_right WHERE id_right = 'APPOINTMENT_DESK_MANAGEMENT';
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url, id_order ) VALUES 
('APPOINTMENT_DESK_MANAGEMENT','appointment-desk.adminFeature.ManageAppointmentDesk.name',1,'jsp/admin/plugins/appointment-desk/ManageAppointmentDesks.jsp','appointment-desk.adminFeature.ManageAppointmentDesk.description',0,'appointment-desk',NULL,NULL,NULL,4);


--
-- Data for table core_user_right
--
DELETE FROM core_user_right WHERE id_right = 'APPOINTMENT_DESK_MANAGEMENT';
INSERT INTO core_user_right (id_right,id_user) VALUES ('APPOINTMENT_DESK_MANAGEMENT',1);

