-- -----------------------------------------------------------------------------------
-- -Eliminacion de tablas y secuencias------------------------------------------------
-- ejecutar desde el usuario
drop synonym SIAT_DYC.dycP_EmailJob;
drop synonym SIAT_DYC.dycH_IntentoJob;
drop synonym SIAT_DYC.dycB_IntentoJob;
drop synonym SIAT_DYC.dycH_EjecucionJob;
drop synonym SIAT_DYC.dycB_EjecucionJob;
drop synonym SIAT_DYC.dycC_Proceso;
drop synonym SIAT_DYC.dycC_EstadoJob;
drop synonym SIAT_DYC.dycT_ParametroJOB;
drop synonym SIAT_DYC.dycC_Parametro;

drop synonym SIAT_DYC.dycQ_EjecucionJob;
drop synonym SIAT_DYC.dycQ_EmailJob;
drop synonym SIAT_DYC.dycQ_IntentoJob;
drop synonym SIAT_DYC.dycQ_PROCESO;

-- ejecutar desde el admin
drop table SIAT_DYC_ADMIN.dycP_EmailJob;
drop table SIAT_DYC_ADMIN.dycH_IntentoJob;
drop table SIAT_DYC_ADMIN.dycB_IntentoJob;
drop table SIAT_DYC_ADMIN.dycH_EjecucionJob;
drop table SIAT_DYC_ADMIN.dycB_EjecucionJob;
drop table SIAT_DYC_ADMIN.dycC_Proceso;
drop table SIAT_DYC_ADMIN.dycC_EstadoJob;
drop table SIAT_DYC_ADMIN.dycT_ParametroJOB;
drop table SIAT_DYC_ADMIN.dycC_Parametro;

DROP SEQUENCE SIAT_DYC_ADMIN.dycQ_EjecucionJob;
DROP SEQUENCE SIAT_DYC_ADMIN.dycQ_EmailJob;
DROP SEQUENCE SIAT_DYC_ADMIN.dycQ_IntentoJob;
DROP SEQUENCE SIAT_DYC_ADMIN.dycQ_PROCESO;

