#go to folder
cd C:/Nir/code/AdxSadna/AdxSadna/SimpleAdx/


#run server:
cd C:/Nir/code/AdxSadna/AdxSadna/SimpleAdx/src/adx-server
sh ./runServer.sh > out_server.txt &

# delay to let server start
sleep 3


# run our agent (after eclipse compile)
cd C:/Nir/code/AdxSadna/AdxSadna/SimpleAdx/bin
sh ./runAgent.sh > out_wizard.txt &

# run giza
cd C:/Nir/studies/adx_sadna/giza/GizaAgentPrivate
sh ./runAgent.sh > out_giza.txt &

# # run BCM
cd C:/Nir/studies/adx_sadna/agents/BCMAdNetwork
sh ./runAgent.sh > out_bcm.txt &


# run IBM
cd C:/Nir/studies/adx_sadna/agents/IBMAdxAgent
sh ./runAgent.sh > out_ibm.txt &


# run OOS
cd C:/Nir/studies/adx_sadna/agents/OOS
sh ./runAgent.sh > out_oos.txt &


# go back to original folder
cd C:/Nir/code/AdxSadna/AdxSadna/SimpleAdx/src


# # run log parser:
# # java -cp "lib/*" se.sics.tasim.logtool.Main -handler tau.tac.adx.parser.GeneralHandler -config C:/Nir/code/AdxSadna/AdxSadna/SimpleAdx/src/adx-server/config/tac13adx_sim.conf -file C:/Nir/code/AdxSadna/AdxSadna/SimpleAdx/src/adx-server/logs/sims/localhost_sim140.slg -ucs -rating -bank -campaign -adnet -all  > log_output/out140.txt