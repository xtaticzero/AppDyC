clear
echo ""
echo "****************************************************"
echo "       Servicio de Administracion Tributaria        "
echo "      Proceso Background PjDictAutomaticas DyC       "
echo "****************************************************"
if pgrep -f "PjDictAutomaticas-1.0.jar"
   then
   echo "Parar proceso background PjDictAutomaticas DyC."
   PID=`pgrep -f "PjDictAutomaticas-1.0.jar"`
   kill -9 "$PID"
   echo "Proceso con id $PID detenido."
else
   echo "Proceso background PjDictAutomaticas DyC NO se esta ejecutando."
fi
   echo "Proceso background PjDictAutomaticas DyC ejecutando..."
#   nohup /usr/local/jrockit-jdk1.6.0_45-R28.2.7-4.1.0/bin/java -jar -Djava.security.egd=file:/dev/../dev/urandom /home/DyC/devautomaticas/lib/PjDictAutomaticas-1.0.jar
   nohup java -jar -Djava.security.egd=file:/dev/../dev/urandom /home/usuario/br_MAT_DyC_10080/PjDictAutomaticas/target/lib/PjDictAutomaticas-1.0.jar
echo "Proceso background PjDictAutomaticas DyC Fin."
