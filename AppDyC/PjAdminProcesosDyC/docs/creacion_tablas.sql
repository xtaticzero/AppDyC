-- -Creacion de tablas y secuencias-------------------------------------

CREATE SEQUENCE SIAT_DYC_ADMIN.dycQ_EjecucionJob
        INCREMENT BY 1
        START WITH 1
        MINVALUE 1
        maxvalue 9999999999999999999999999999
        CYCLE
        NOCACHE
        ORDER;
CREATE SYNONYM SIAT_DYC.dycQ_EjecucionJob FOR SIAT_DYC_ADMIN.dycQ_EjecucionJob;
grant select on SIAT_DYC_ADMIN.dycQ_EjecucionJob TO DYCR_SIAT;


CREATE SEQUENCE SIAT_DYC_ADMIN.dycQ_EmailJob
	INCREMENT BY 1
	START WITH 1
	MINVALUE 1
        maxvalue 9999999999999999999999999999
	NOCACHE
	ORDER;
CREATE SYNONYM SIAT_DYC.dycQ_EmailJob FOR SIAT_DYC_ADMIN.dycQ_EmailJob;
grant select on SIAT_DYC_ADMIN.dycQ_EmailJob TO DYCR_SIAT;


CREATE SEQUENCE SIAT_DYC_ADMIN.dycQ_IntentoJob
	INCREMENT BY 1
	START WITH 1
	MINVALUE 1
        maxvalue 9999999999999999999999999999
	CYCLE
	NOCACHE
	ORDER;
CREATE SYNONYM SIAT_DYC.dycQ_IntentoJob FOR SIAT_DYC_ADMIN.dycQ_IntentoJob;
grant select on SIAT_DYC_ADMIN.dycQ_IntentoJob TO DYCR_SIAT;

CREATE SEQUENCE SIAT_DYC_ADMIN.dycQ_PROCESO
	INCREMENT BY 1
	START WITH 10
	MINVALUE 1
        maxvalue 9999999999999999999999999999
	NOCACHE
	ORDER;
CREATE OR REPLACE SYNONYM  SIAT_DYC.dycQ_PROCESO FOR SIAT_DYC_ADMIN.dycQ_PROCESO;
grant select on SIAT_DYC_ADMIN.dycQ_PROCESO TO DYCR_SIAT;


CREATE TABLE SIAT_DYC_ADMIN.dycC_Proceso
(
	IdProceso            NUMBER(2) NOT NULL ,
	Nombre               VARCHAR2(100 CHAR) NOT NULL ,
	Descripcion          VARCHAR2(150 CHAR) NOT NULL ,
	InicioEjecucion      DATE NULL ,
	FinEjecucion         DATE NULL ,
	Lanzador             VARCHAR2(100 CHAR) NULL ,
	Programacion         VARCHAR2(100 CHAR) NULL ,
	Estado               NUMBER(1) NULL ,
	Intentos             NUMBER(1) NULL ,
	IntentosMax          NUMBER(1) NULL ,
	Excluir              VARCHAR2(73 CHAR) NULL ,
	Prioridad            NUMBER(1) NULL ,
	IdManager            VARCHAR2(73 CHAR) NULL ,
	TipoProcesamiento    NUMBER(1) NULL ,
	FechaUltimoAvance    DATE NOT NULL ,
        FechaInicio          DATE NULL ,
        FechaFin             DATE NULL ,
CONSTRAINT  PKdycC_Proceso PRIMARY KEY (IdProceso)  USING INDEX
	INITRANS 10
	TABLESPACE DYCLI_SIAT
)
	TABLESPACE DYCLC_SIAT
	INITRANS 5;
CREATE SYNONYM SIAT_DYC.dycC_Proceso FOR SIAT_DYC_ADMIN.dycC_Proceso;
grant select, insert on SIAT_DYC_ADMIN.dycC_Proceso TO DYCR_SIAT;
GRANT UPDATE (nombre, descripcion, lanzador, programacion, estado, intentos, intentosMax, excluir, prioridad, idManager, inicioEjecucion, finEjecucion, tipoProcesamiento, fechaUltimoAvance) ON SIAT_DYC_ADMIN.dycC_Proceso TO DYCR_SIAT;


CREATE TABLE SIAT_DYC_ADMIN.dycC_EstadoJob
(
	IdEstadoJob          NUMBER(1) NOT NULL ,
	Descripcion          VARCHAR2(150 CHAR) NOT NULL ,
	Nombre               VARCHAR2(100 CHAR) NOT NULL ,
	FechaInicio          DATE NOT NULL ,
	FechaFin             DATE NULL ,
	Orden                NUMBER(1) NOT NULL ,
CONSTRAINT  PK_dycCEstadoJob PRIMARY KEY (IdEstadoJob)  USING INDEX
	INITRANS 10
	TABLESPACE DYCLI_SIAT
)
	TABLESPACE DYCLC_SIAT
	INITRANS 5;

CREATE SYNONYM SIAT_DYC.dycC_EstadoJob FOR SIAT_DYC_ADMIN.dycC_EstadoJob;
GRANT SELECT ON SIAT_DYC_ADMIN.dycC_EstadoJob TO DYCR_SIAT;

INSERT INTO SIAT_DYC_ADMIN.dycC_EstadoJob (IDESTADOJOB,DESCRIPCION,NOMBRE,FECHAINICIO,FECHAFIN,ORDEN) VALUES (0,'Deshabilitado','Deshabilitado',sysdate,null,1);
INSERT INTO SIAT_DYC_ADMIN.dycC_EstadoJob (IDESTADOJOB,DESCRIPCION,NOMBRE,FECHAINICIO,FECHAFIN,ORDEN) VALUES (1,'Habilitado','Habilitado',sysdate,null,1);
INSERT INTO SIAT_DYC_ADMIN.dycC_EstadoJob (IDESTADOJOB,DESCRIPCION,NOMBRE,FECHAINICIO,FECHAFIN,ORDEN) VALUES (2,'En espera','En espera',sysdate,null,1);
INSERT INTO SIAT_DYC_ADMIN.dycC_EstadoJob (IDESTADOJOB,DESCRIPCION,NOMBRE,FECHAINICIO,FECHAFIN,ORDEN) VALUES (3,'Por Ejecutar','Por Ejecutar',sysdate,null,1);
INSERT INTO SIAT_DYC_ADMIN.dycC_EstadoJob (IDESTADOJOB,DESCRIPCION,NOMBRE,FECHAINICIO,FECHAFIN,ORDEN) VALUES (4,'En Ejecución','En Ejecución',sysdate,null,1);
INSERT INTO SIAT_DYC_ADMIN.dycC_EstadoJob (IDESTADOJOB,DESCRIPCION,NOMBRE,FECHAINICIO,FECHAFIN,ORDEN) VALUES (5,'Completado','Completado',sysdate,null,1);
INSERT INTO SIAT_DYC_ADMIN.dycC_EstadoJob (IDESTADOJOB,DESCRIPCION,NOMBRE,FECHAINICIO,FECHAFIN,ORDEN) VALUES (6,'Fallido','Fallido',sysdate,null,1);
INSERT INTO SIAT_DYC_ADMIN.dycC_EstadoJob (IDESTADOJOB,DESCRIPCION,NOMBRE,FECHAINICIO,FECHAFIN,ORDEN) VALUES (7,'Intentos Terminados','Intentos Terminados',sysdate,null,1);


CREATE TABLE SIAT_DYC_ADMIN.dycB_EjecucionJob
(
	IdEjecucion          NUMBER(22) NOT NULL ,
	IdProceso            NUMBER(2) NOT NULL ,
	Intento              NUMBER(2) NOT NULL ,
	FechaInicio          DATE NOT NULL ,
	FechaFin             DATE NULL ,
	Observaciones        VARCHAR2(500 CHAR) NULL ,
	IdEstadoJob          NUMBER(1) NOT NULL ,
CONSTRAINT  PK_dycBEjecucionJob PRIMARY KEY (IdEjecucion)  USING INDEX
	INITRANS 10
	TABLESPACE DYCLI_SIAT,
CONSTRAINT FK_dycCProceso_dycBEje FOREIGN KEY (IdProceso) REFERENCES SIAT_DYC_ADMIN.dycC_Proceso (IdProceso),
CONSTRAINT FK_dycCEstadoJob_dycBEje FOREIGN KEY (IdEstadoJob) REFERENCES SIAT_DYC_ADMIN.dycC_EstadoJob (IdEstadoJob)
)
	TABLESPACE DYCLT_SIAT
	INITRANS 10;
CREATE OR REPLACE SYNONYM SIAT_DYC.dycB_EjecucionJob FOR SIAT_DYC_ADMIN.dycB_EjecucionJob;
GRANT SELECT, DELETE, INSERT ON SIAT_DYC_ADMIN.dycB_EJECUCIONJOB TO DYCR_SIAT;
GRANT UPDATE (idEstadoJob, intento, fechaFin, observaciones) ON SIAT_DYC_ADMIN.dycB_EjecucionJob TO DYCR_SIAT;


CREATE TABLE SIAT_DYC_ADMIN.dycB_IntentoJob
(
	IdIntentoJob         NUMBER(22) NOT NULL ,
	IdEjecucion          NUMBER(22) NOT NULL ,
	Intento              NUMBER(2) NOT NULL ,
	FechaInicio          DATE NOT NULL ,
	FechaFin             DATE NULL ,
	Observaciones        VARCHAR2(500 CHAR) NULL ,
	IdEstadoJob          NUMBER(1) NOT NULL ,
CONSTRAINT  PK_dycBIntentoJob PRIMARY KEY (IdIntentoJob)  USING INDEX
	INITRANS 10
	TABLESPACE DYCLI_SIAT,
CONSTRAINT FK_dycBEjecJob_dycBInte FOREIGN KEY (IdEjecucion) REFERENCES SIAT_DYC_ADMIN.dycB_EjecucionJob (IdEjecucion),
CONSTRAINT FK_dycCEstJob_dycBInteJ FOREIGN KEY (IdEstadoJob) REFERENCES SIAT_DYC_ADMIN.dycC_EstadoJob (IdEstadoJob)
)
	TABLESPACE DYCLT_SIAT
	INITRANS 10;
CREATE OR REPLACE SYNONYM SIAT_DYC.dycB_IntentoJob FOR SIAT_DYC_ADMIN.dycB_IntentoJob;
GRANT SELECT, DELETE, INSERT ON SIAT_DYC_ADMIN.dycB_INTENTOJOB TO DYCR_SIAT;
GRANT UPDATE, DELETE ON SIAT_DYC_ADMIN.dycB_IntentoJob TO DYCR_SIAT;


CREATE TABLE SIAT_DYC_ADMIN.dycH_EjecucionJob
(
	IdEjecucion          NUMBER(22) NOT NULL ,
	IdProceso            NUMBER(2) NOT NULL ,
	Intento              NUMBER(2) NOT NULL ,
	FechaInicio          DATE NOT NULL ,
	FechaFin             DATE NULL ,
	Observaciones        VARCHAR2(500 CHAR) NULL ,
	IdEstadoJob          NUMBER(1) NOT NULL ,
CONSTRAINT FK_dycCPRO_dycHEJEJ FOREIGN KEY (IdProceso) REFERENCES SIAT_DYC_ADMIN.dycC_Proceso (IdProceso),
CONSTRAINT FK_dycCESTJOB_dycHEJE FOREIGN KEY (IdEstadoJob) REFERENCES SIAT_DYC_ADMIN.dycC_EstadoJob (IdEstadoJob)
)
	TABLESPACE DYCLT_SIAT
	INITRANS 10;
CREATE SYNONYM SIAT_DYC.dycH_EjecucionJob FOR SIAT_DYC_ADMIN.dycH_EjecucionJob;
GRANT SELECT, INSERT ON SIAT_DYC_ADMIN.dycH_EjecucionJOB TO DYCR_SIAT;


CREATE TABLE SIAT_DYC_ADMIN.dycH_IntentoJob
(
	IdIntentoJob         NUMBER(22) NULL ,
	Intento              NUMBER(2) NOT NULL ,
	FechaInicio          DATE NOT NULL ,
	FechaFin             DATE NULL ,
	Observaciones        VARCHAR2(500 CHAR) NULL ,
	IdEstadoJob          NUMBER(1) NOT NULL ,
	IdEjecucion          NUMBER(22) NOT NULL ,
CONSTRAINT FK_dycCESTJOB_dycHINT FOREIGN KEY (IdEstadoJob) REFERENCES SIAT_DYC_ADMIN.dycC_EstadoJob (IdEstadoJob)
)
	TABLESPACE DYCLT_SIAT
	INITRANS 10;
CREATE SYNONYM SIAT_DYC.dycH_IntentoJob FOR SIAT_DYC_ADMIN.dycH_IntentoJob;
GRANT SELECT, INSERT ON SIAT_DYC_ADMIN.dycH_INTENTOJOB TO DYCR_SIAT;


CREATE TABLE SIAT_DYC_ADMIN.dycP_EmailJob
(
	NombreCompleto       VARCHAR2(200 CHAR) NOT NULL ,
	CorreoElectronico    VARCHAR2(150 CHAR) NOT NULL ,
	CorreoElectronicoAlterno VARCHAR2(150 CHAR) NULL ,
	FechaInicio          DATE NOT NULL ,
	FechaFin             DATE NULL ,
	IdEmailProcesoDetenido INTEGER NOT NULL ,
CONSTRAINT  PK_dycPEmailJob PRIMARY KEY (IdEmailProcesoDetenido)  USING INDEX
	INITRANS 10
	TABLESPACE DYCLI_SIAT
)
	TABLESPACE DYCLC_SIAT
	INITRANS 10;
CREATE SYNONYM SIAT_DYC.dycP_EmailJob FOR SIAT_DYC_ADMIN.dycP_EmailJob;
GRANT SELECT, INSERT ON SIAT_DYC_ADMIN.dycP_EmailJob TO DYCR_SIAT;
grant update(NombreCompleto, CorreoElectronico, CorreoElectronicoAlterno, FechaFin) on  SIAT_DYC_ADMIN.dycP_EMAILJOB to DYCR_SIAT;


CREATE TABLE SIAT_DYC_ADMIN.dycC_Parametro
(
	IdParametro          NUMBER(2) NOT NULL ,
	Nombre               VARCHAR2(100 CHAR) NOT NULL ,
	Descripcion          VARCHAR2(150 CHAR) NOT NULL ,
	FechaInicio          DATE NOT NULL ,
	FechaFin             DATE NULL ,
	Orden                NUMBER(2) NULL ,
	TipoDato             CHAR(1 CHAR) NULL ,
	Precision            VARCHAR2(4 CHAR) NULL ,
CONSTRAINT  PK_dycCParametro PRIMARY KEY (IdParametro)  USING INDEX
	INITRANS 10
	TABLESPACE DYCLI_SIAT
)
	TABLESPACE DYCLC_SIAT
	INITRANS 5;
CREATE SYNONYM SIAT_DYC.dycC_Parametro FOR SIAT_DYC_ADMIN.dycC_Parametro;
GRANT SELECT ON SIAT_DYC_ADMIN.dycC_Parametro TO DYCR_SIAT;

INSERT INTO SIAT_DYC_ADMIN.dycC_Parametro (IDPARAMETRO,NOMBRE,DESCRIPCION,FECHAINICIO,FECHAFIN,ORDEN,TIPODATO,PRECISION) VALUES (6,'Ambiente','Descripción del ambiente donde reside el aplicativo',sysdate,null,1,'S',null);
INSERT INTO SIAT_DYC_ADMIN.dycC_Parametro (IDPARAMETRO,NOMBRE,DESCRIPCION,FECHAINICIO,FECHAFIN,ORDEN,TIPODATO,PRECISION) VALUES (9,'Para envió de email, tiempo considerado excesivo para un proceso "en ejecución" (Minutos)','Para envió de email, tiempo considerado excesivo para un proceso "en ejecución" (Minutos)',sysdate,null,1,'N','5');
INSERT INTO SIAT_DYC_ADMIN.dycC_Parametro (IDPARAMETRO,NOMBRE,DESCRIPCION,FECHAINICIO,FECHAFIN,ORDEN,TIPODATO,PRECISION) VALUES (10,'Para envió de email, tiempo considerado excesivo para un proceso "por ejecutar" (Minutos)','Para envió de email, tiempo considerado excesivo para un proceso "por ejecutar" (Minutos)',sysdate,null,1,'N','5');
INSERT INTO SIAT_DYC_ADMIN.dycC_Parametro (IDPARAMETRO,NOMBRE,DESCRIPCION,FECHAINICIO,FECHAFIN,ORDEN,TIPODATO,PRECISION) VALUES (11,'Intervalo para envió de email por tiempo excesivo para un proceso "en ejecución" (Minutos)','Intervalo para envió de email por tiempo excesivo para un proceso "en ejecución" (Minutos)',sysdate,null,1,'N','2');
INSERT INTO SIAT_DYC_ADMIN.dycC_Parametro (IDPARAMETRO,NOMBRE,DESCRIPCION,FECHAINICIO,FECHAFIN,ORDEN,TIPODATO,PRECISION) VALUES (16,'Tiempo de reactivación de proceso con intentos agotados','Tiempo de reactivación de proceso con intentos agotados',sysdate,null,1,'N','3');
INSERT INTO SIAT_DYC_ADMIN.dycC_Parametro (IDPARAMETRO,NOMBRE,DESCRIPCION,FECHAINICIO,FECHAFIN,ORDEN,TIPODATO,PRECISION) VALUES (17,'Para marcaje fallido de proceso, tiempo considerado excesivo desde ultimo reporte de actividad','Para marcaje fallido de proceso, tiempo considerado excesivo desde ultimo reporte de actividad',sysdate,null,1,'N','3');


CREATE TABLE SIAT_DYC_ADMIN.dycT_ParametroJOB
(
	IdParametro          NUMBER(2) NULL ,
	Valor                VARCHAR2(250 CHAR) NULL ,
	FechaInicio          DATE NOT NULL ,
	FechaFin             DATE NULL ,
CONSTRAINT FK_dycCParam_dycTParamdyc FOREIGN KEY (IdParametro) REFERENCES SIAT_DYC_ADMIN.dycC_Parametro (IdParametro)
)
	TABLESPACE DYCLT_SIAT
	INITRANS 10;
CREATE SYNONYM SIAT_DYC.dycT_ParametroJOB FOR SIAT_DYC_ADMIN.dycT_ParametroJOB;
GRANT SELECT, INSERT, UPDATE ON SIAT_DYC_ADMIN.dycT_ParametroJOB TO DYCR_SIAT;

Insert into SIAT_DYC_ADMIN.dycT_ParametroJOB (IDPARAMETRO,VALOR,FECHAINICIO,FECHAFIN) values ('6','UAT',to_date('13/11/15','DD/MM/RR'),null);
Insert into SIAT_DYC_ADMIN.dycT_ParametroJOB (IDPARAMETRO,VALOR,FECHAINICIO,FECHAFIN) values ('9','185',to_date('22/01/15','DD/MM/RR'),null);
Insert into SIAT_DYC_ADMIN.dycT_ParametroJOB (IDPARAMETRO,VALOR,FECHAINICIO,FECHAFIN) values ('10','5',to_date('01/01/14','DD/MM/RR'),null);
Insert into SIAT_DYC_ADMIN.dycT_ParametroJOB (IDPARAMETRO,VALOR,FECHAINICIO,FECHAFIN) values ('11','30',to_date('01/01/14','DD/MM/RR'),null);
Insert into SIAT_DYC_ADMIN.dycT_ParametroJOB (IDPARAMETRO,VALOR,FECHAINICIO,FECHAFIN) values ('16','60',to_date('09/07/15','DD/MM/RR'),null);
Insert into SIAT_DYC_ADMIN.dycT_ParametroJOB (IDPARAMETRO,VALOR,FECHAINICIO,FECHAFIN) values ('17','60',to_date('09/07/15','DD/MM/RR'),null);

Insert into DYCP_EMAILJOB (NOMBRECOMPLETO,CORREOELECTRONICO,CORREOELECTRONICOALTERNO,FECHAINICIO,FECHAFIN,IDEMAILPROCESODETENIDO) values ('nombre usuario','algun.correo@algundominiodecorreo.com',null,sysdate,null,'1');

Insert into dycc_proceso (IDPROCESO,NOMBRE,DESCRIPCION,INICIOEJECUCION,FINEJECUCION,LANZADOR,PROGRAMACION,ESTADO,INTENTOS,INTENTOSMAX,EXCLUIR,PRIORIDAD,IDMANAGER,TIPOPROCESAMIENTO,FECHAULTIMOAVANCE,FECHAINICIO,FECHAFIN) values 
('1','envioANyVService.buscarDocumentosAEnviar','envio a Ny V',null,null,null,'0 40 9 * * ?','0','0','2',null,'2',null,'2',sysdate,null,null);
Insert into dycc_proceso (IDPROCESO,NOMBRE,DESCRIPCION,INICIOEJECUCION,FINEJECUCION,LANZADOR,PROGRAMACION,ESTADO,INTENTOS,INTENTOSMAX,EXCLUIR,PRIORIDAD,IDMANAGER,TIPOPROCESAMIENTO,FECHAULTIMOAVANCE,FECHAINICIO,FECHAFIN) values 
('2','casoCompensacionPrincipalService.procesoCasoCompensacion','procesDYC',null,null,null,'0 0 22 * * ?','0','0','4',null,'2',null,'2',sysdate,null,null);
Insert into dycc_proceso (IDPROCESO,NOMBRE,DESCRIPCION,INICIOEJECUCION,FINEJECUCION,LANZADOR,PROGRAMACION,ESTADO,INTENTOS,INTENTOSMAX,EXCLUIR,PRIORIDAD,IDMANAGER,TIPOPROCESAMIENTO,FECHAULTIMOAVANCE,FECHAINICIO,FECHAFIN) values 
('3','retroalimentarTESOFEService.retroalimentarTESOFE','retroalimentacionTESOFEJAR',null,null,null,'0 0 21 * * ?','0','0','4',null,'2',null,'2',sysdate,null,null);
Insert into dycc_proceso (IDPROCESO,NOMBRE,DESCRIPCION,INICIOEJECUCION,FINEJECUCION,LANZADOR,PROGRAMACION,ESTADO,INTENTOS,INTENTOSMAX,EXCLUIR,PRIORIDAD,IDMANAGER,TIPOPROCESAMIENTO,FECHAULTIMOAVANCE,FECHAINICIO,FECHAFIN) values 
('4','procesarDatosARCAService.procesarDatosArca','PjNotificacionesYVerificaciones',null,null,null,'0 40 9 * * ?','0','0','4',null,'2',null,'2',sysdate,null,null);
Insert into dycc_proceso (IDPROCESO,NOMBRE,DESCRIPCION,INICIOEJECUCION,FINEJECUCION,LANZADOR,PROGRAMACION,ESTADO,INTENTOS,INTENTOSMAX,EXCLUIR,PRIORIDAD,IDMANAGER,TIPOPROCESAMIENTO,FECHAULTIMOAVANCE,FECHAINICIO,FECHAFIN) values 
('5','validarReqsServiceImpl.execute','validarReqJAR',null,null,null,'0 0 21 * * ?','0','0','4',null,'2',null,'2',sysdate,null,null);
Insert into dycc_proceso (IDPROCESO,NOMBRE,DESCRIPCION,INICIOEJECUCION,FINEJECUCION,LANZADOR,PROGRAMACION,ESTADO,INTENTOS,INTENTOSMAX,EXCLUIR,PRIORIDAD,IDMANAGER,TIPOPROCESAMIENTO,FECHAULTIMOAVANCE,FECHAINICIO,FECHAFIN) values 
('6','procesoDeclaracionComplemenService.selectXDeclaracionComplemen','PjComplementarias',null,null,null,'0 0 22 * * ?','0','0','4',null,'2',null,'2',sysdate,null,null);
Insert into dycc_proceso (IDPROCESO,NOMBRE,DESCRIPCION,INICIOEJECUCION,FINEJECUCION,LANZADOR,PROGRAMACION,ESTADO,INTENTOS,INTENTOSMAX,EXCLUIR,PRIORIDAD,IDMANAGER,TIPOPROCESAMIENTO,FECHAULTIMOAVANCE,FECHAINICIO,FECHAFIN) values 
('7','obtenerInformacionAProcesarService.extraerDocumentos','PjExtractorDeAnexos',null,null,null,'0 0 23 * * ?','0','0','4',null,'2',null,'2',sysdate,null,null);
commit;
