REM go to folder
cd C:/Nir/code/AdxSadna/AdxSadna/SimpleAdx/



REM run server:
cd C:/Nir/code/AdxSadna/AdxSadna/SimpleAdx/src/adx-server
start java -cp "lib/*" se.sics.tasim.sim.Main

REM delay to let server start
timeout 5


REM run our agent (after eclipse compile)
cd C:/Nir/code/AdxSadna/AdxSadna/SimpleAdx/bin
start java -cp "lib/*;." tau.tac.adx.agentware.Main -config config/aw-1.conf

REM run giza
cd C:/Nir/studies/adx_sadna/giza/GizaAgentPrivate
start java -cp "lib/*;." tau.tac.adx.agentware.Main -config config/aw-1.conf

REM run BCM
cd C:\Nir\studies\adx_sadna\agents\BCMAdNetwork
start java -cp "lib/*;." tau.tac.adx.agentware.Main -config config/aw-1.conf

REM run IBM
cd C:\Nir\studies\adx_sadna\agents\IBMAdxAgent
start java -cp "lib/*;." tau.tac.adx.agentware.Main -config config/aw-1.conf

REM run OOS
cd C:\Nir\studies\adx_sadna\agents\OOS
start java -cp "lib/*;." tau.tac.adx.agentware.Main -config config/aw-1.conf


REM go back to original folder
cd C:/Nir/code/AdxSadna/AdxSadna/SimpleAdx/src


REM run log parser:
REM java -cp "lib/*" se.sics.tasim.logtool.Main -handler tau.tac.adx.parser.GeneralHandler -config C:\Nir\code\AdxSadna\AdxSadna\SimpleAdx\src\adx-server\config\tac13adx_sim.conf -file C:\Nir\code\AdxSadna\AdxSadna\SimpleAdx\src\adx-server\logs\sims\localhost_sim140.slg -ucs -rating -bank -campaign -adnet -all  > log_output/out140.txt