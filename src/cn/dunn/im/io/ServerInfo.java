package cn.dunn.im.io;

import java.net.InetAddress;
import java.text.DecimalFormat;
import java.util.*;

import org.hyperic.sigar.*;

import cn.dunn.im.constant.ConnectConstants;




public class ServerInfo {  
	
	
//public static void main(String[] args) throws Exception {
//	
//}

public ServerInfoVo start(){  
	
	
	 //服务信息  
    ServerInfoVo serverInfoVo = new  ServerInfoVo();  
	
    try {  
        SigarUtils.initSigar();         //初始化动态库链接路径  
    
        Properties props=System.getProperties();  
        Runtime runTime = Runtime.getRuntime();  
        InetAddress address = InetAddress.getLocalHost();  
              
       
        InetAddress.getLocalHost().getHostAddress();  
        serverInfoVo.setServerIP(address.getHostAddress());  
      
        serverInfoVo.setServerTime(new Date());  
              
        //操作系统  
        serverInfoVo.setOsName(props.getProperty("os.name")+"  "+props.getProperty("os.arch"));  
        serverInfoVo.setOsVersion(props.getProperty("os.version"));  
        serverInfoVo.setUserName(props.getProperty("user.name"));  
        serverInfoVo.setUserHome(props.getProperty("user.home"));  
        Calendar cal = Calendar.getInstance();  
        TimeZone timeZone = cal.getTimeZone();  
        serverInfoVo.setOsTimeZone(timeZone.getDisplayName());  
              
         
        Sigar sigar = new Sigar();  
        
        	DecimalFormat df = new DecimalFormat("#0.00");
        	//Cpu 
        	CpuPerc cpu = sigar.getCpuPerc();
        	
        	Double cpuFree = cpu.getIdle();
    		String cpuFreeFormat = CpuPerc.format(cpuFree);
    		Double cpuUsed = cpu.getCombined();
    		String cpuUsedFormat = CpuPerc.format(cpuUsed);
    		
        	serverInfoVo.setCpuFree(cpuFree);
        	serverInfoVo.setCpuFreeFormat(cpuFreeFormat);
        	serverInfoVo.setCpuUsed(cpuUsed); 
        	serverInfoVo.setCpuUsedFormat(cpuUsedFormat); 
        	
        
            CpuInfo cpuInfos[] = sigar.getCpuInfoList();  
            serverInfoVo.setCpuNum(cpuInfos.length);  
            CpuPerc cpuList[] = sigar.getCpuPercList();  
            List<CpuInfoVo> cpuVoList = new ArrayList<CpuInfoVo>();
            for(int m=0;m<cpuList.length;m++){  
                CpuInfoVo cpuInfoVo = new CpuInfoVo();  
                cpuInfoVo.setCpuMhz(cpuInfos[m].getMhz());  
                cpuInfoVo.setVendor(cpuInfos[m].getVendor());
                cpuInfoVo.setModel(cpuInfos[m].getModel());
                String cpuIdle = String.format("%.2f",cpuList[m].getIdle()*100)+"%";  
                cpuInfoVo.setCpuLdle(cpuIdle);
                String cpuCombined = String.format("%.2f",cpuList[m].getCombined()*100)+"%";  
                cpuInfoVo.setCpuCombined(cpuCombined);  
                cpuVoList.add(cpuInfoVo);  
            }  
            serverInfoVo.setCpuList(cpuVoList);  
              
            //磁盘 
            
            
        	//FileSystem fslist[] = sigar.getFileSystemList();
            
        	FileSystem[] fslist = sigar.getFileSystemList();
        	
        	List<HddInfoVo> fsVoList = new ArrayList<HddInfoVo>();	
        	 for(int d=0;d<fslist.length;d++){  
        		 HddInfoVo hddInfoVo = new HddInfoVo();  
        		 hddInfoVo.setDevName(fslist[d].getDevName());
        		 hddInfoVo.setDirName(fslist[d].getDirName());
        		 hddInfoVo.setFlags(fslist[d].getFlags());
        		 hddInfoVo.setSysTypeName(fslist[d].getSysTypeName());
        		 hddInfoVo.setTypeName(fslist[d].getTypeName());
        		 hddInfoVo.setTypeNumber(fslist[d].getType()); 
        		 
        		 if(fslist[d].getType() == 2) { 
        			FileSystemUsage usage = null;
    				try {
    					usage = sigar.getFileSystemUsage(fslist[d].getDirName());
    				} catch (SigarException e) {
    					//e.printStackTrace();
    				}
        			 hddInfoVo.setTotal(df.format((float) usage.getTotal() / 1024 / 1024) + "G");
        			 hddInfoVo.setFree(df.format((float) usage.getFree() / 1024 / 1024) + "G");
        			 hddInfoVo.setAvail(df.format((float) usage.getAvail() / 1024 / 1024) + "G");
        			 hddInfoVo.setUsed(df.format((float) usage.getUsed() / 1024 / 1024) + "G");
        			 double usePercent = usage.getUsePercent() * 100D;
        			 hddInfoVo.setUsage(df.format(usePercent) + "%");
        			 hddInfoVo.setDiskReads(usage.getDiskReads());
        			 hddInfoVo.setDiskWrites(usage.getDiskWrites()); 
        			 
        			 
        			 
        		 }
        		 fsVoList.add(hddInfoVo);   
        	 }
            
        	 serverInfoVo.setHddList(fsVoList);  
            
            //网卡信息  
            String netInfos[] = sigar.getNetInterfaceList();  
            List<NetInterfaceConfig> netList = new ArrayList<NetInterfaceConfig>();  
            String hwaddr = null;
            
            for(int i=0;i<netInfos.length;i++){  
                NetInterfaceConfig netInfo = sigar.getNetInterfaceConfig(netInfos[i]);   
                if((NetFlags.LOOPBACK_ADDRESS.equals(netInfo.getAddress()))     //127.0.0.1   
                        || (netInfo.getFlags() == 0)                            //标识为0  
                        || (NetFlags.NULL_HWADDR.equals(netInfo.getHwaddr()))   //MAC地址不存在  
                        || (NetFlags.ANY_ADDR.equals(netInfo.getAddress()))     //0.0.0.0  
                        ){  
                    continue;  
                }  
                netList.add(netInfo);  
            }  
            for(int i=0;i<netInfos.length;i++){  
                NetInterfaceConfig netInfo = sigar.getNetInterfaceConfig(netInfos[i]);   
                if((NetFlags.LOOPBACK_ADDRESS.equals(netInfo.getAddress()))     //127.0.0.1   
                        || (netInfo.getFlags() == 0)                            //标识为0  
                        || (NetFlags.NULL_HWADDR.equals(netInfo.getHwaddr()))   //MAC地址不存在  
                        || (NetFlags.ANY_ADDR.equals(netInfo.getAddress()))     //0.0.0.0  
                        ){  
                    continue;  
                }  
                hwaddr = netInfo.getHwaddr();
				break;
            }
            serverInfoVo.setNetNum(netList.size());  
            serverInfoVo.setNetList(netList);  
            serverInfoVo.setMAC(hwaddr);  
              
            //物理内存  
            Mem mem = sigar.getMem();   
            //虚拟内存  
            Swap swap = sigar.getSwap();
            
          
            
            serverInfoVo.setMemTotal(mem.getTotal());  
            serverInfoVo.setMemUsed(mem.getUsed());  
            serverInfoVo.setMemFree(mem.getFree());
            serverInfoVo.setMemTotalFormat(df.format(((float)mem.getTotal()/(1024*1024*1024)))+"G");  
            serverInfoVo.setMemUsedFormat(df.format(((float)mem.getUsed()/(1024*1024*1024)))+"G");  
            serverInfoVo.setMemFreeFormat(df.format(((float)mem.getFree()/(1024*1024*1024)))+"G");  
            
            
            
            serverInfoVo.setSwapTotal(swap.getTotal());  
            serverInfoVo.setSwapUsed(swap.getUsed());  
            serverInfoVo.setSwapFree(swap.getFree());
            serverInfoVo.setSwapTotalFormat(df.format(((float)swap.getTotal()/(1024*1024*1024)))+"G");  
            serverInfoVo.setSwapUsedFormat(df.format(((float)swap.getUsed()/(1024*1024*1024)))+"G");  
            serverInfoVo.setSwapFreeFormat(df.format(((float)swap.getFree()/(1024*1024*1024)))+"G");  
            
              
        //java配置  
        serverInfoVo.setJavaPath(props.getProperty("java.home"));  
        serverInfoVo.setJavaVendor(props.getProperty("java.vendor"));  
        serverInfoVo.setJavaVersion(props.getProperty("java.version"));  
        serverInfoVo.setJavaName(props.getProperty("java.specification.name"));  
        serverInfoVo.setJvmVersion(props.getProperty("java.vm.version"));  
        serverInfoVo.setJvmTotalMemory((int)(runTime.totalMemory()/(1024*1024))+"M");  
        serverInfoVo.setJvmFreeMemory((int)(runTime.freeMemory()/(1024*1024))+"M");  
        
        return serverInfoVo;  
    } catch (Exception e) {  
        e.printStackTrace();  
    }
	return serverInfoVo;  
}  
}