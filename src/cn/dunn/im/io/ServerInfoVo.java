package cn.dunn.im.io;

import java.util.Date;  
import java.util.List;  
  
import org.hyperic.sigar.NetInterfaceConfig;  
  
public class ServerInfoVo {  
  
    private String serverIP;        //服务器IP  
    private String serverURL;       //服务器Url  
    private String serverType;      //服务器类型  
    private Date serverTime;        //服务器时间  
      
    private String osName;          //操作系统名称  
    private String osVersion;       //操作系统版本  
    private String userName;        //用户名称  
    private String userHome;        //用户主目录  
    private String osTimeZone;      //操作系统时区  
      
    private Long memTotal;        //物理内存总量  
    private Long memUsed;         //已使用的物理内存  
    private Long memFree;         //物理内存剩余量  
    private String memTotalFormat;        //物理内存总量  
    private String memUsedFormat;         //已使用的物理内存  
    private String memFreeFormat;         //物理内存剩余量  
    
    
    private Long swapTotal;        //虚拟内存总量  
    private Long swapUsed;         //已使用的虚拟内存  
    private Long swapFree;         //虚拟内存剩余量  
    private String swapTotalFormat;        //虚拟内存总量  
    private String swapUsedFormat;         //已使用的虚拟内存  
    private String swapFreeFormat;         //虚拟内存剩余量    
    
    
    private Double cpuFree;         //可用总处理量  
    private String cpuFreeFormat;         //可用总处理量 
    private Double cpuUsed;         //已使用的总处理量   
    private String cpuUsedFormat;         //已使用的总处理量     
    
    
    private String mac;         //mac地址        
    private int cpuNum;             //cpu总数  
    private List<CpuInfoVo> cpuList;//cpu信息  
    private List<HddInfoVo> hddList;//cpu信息  
      
    private int netNum;         //网卡总数  
    private List<NetInterfaceConfig> netList; //网卡信息  
      
    private String javaPath;        //java安路径  
    private String javaVendor;      //java运行时供应商  
    private String javaVersion;     //java版本  
    private String javaName;        //java运行时名称  
    private String jvmVersion;      //java虚拟机版本  
    private String jvmTotalMemory;  //java虚拟机总内存  
    private String jvmFreeMemory;   //java虚拟机剩余内存  
      
    private String databaseType;    //数据库类型  
    private String databaseVersion; //数据库类型  
    private String databaseDriver;  //数据库驱动  
    private String driverVersion;   //数据库驱动版本  
    private String jdbcUrl;         //数据库连接url  
      
    public String getServerIP() {  
        return serverIP;  
    }  
    public void setServerIP(String serverIP) {  
        this.serverIP = serverIP;  
    }  
    public String getServerURL() {  
        return serverURL;  
    }  
    public void setServerURL(String serverURL) {  
        this.serverURL = serverURL;  
    }  
    public String getServerType() {  
        return serverType;  
    }  
    public void setServerType(String serverType) {  
        this.serverType = serverType;  
    }  
    public String getOsName() {  
        return osName;  
    }  
    public void setOsName(String osName) {  
        this.osName = osName;  
    }  
    public String getOsVersion() {  
        return osVersion;  
    }  
    public void setOsVersion(String osVersion) {  
        this.osVersion = osVersion;  
    }  
    public String getUserName() {  
        return userName;  
    }  
    public void setUserName(String userName) {  
        this.userName = userName;  
    }  
    public String getUserHome() {  
        return userHome;  
    }  
    public void setUserHome(String userHome) {  
        this.userHome = userHome;  
    }  
    public String getOsTimeZone() {  
        return osTimeZone;  
    }  
    public void setOsTimeZone(String osTimeZone) {  
        this.osTimeZone = osTimeZone;  
    }  
    public String getJavaVendor() {  
        return javaVendor;  
    }  
    public void setJavaVendor(String javaVendor) {  
        this.javaVendor = javaVendor;  
    }  
    public String getJavaVersion() {  
        return javaVersion;  
    }  
    public void setJavaVersion(String javaVersion) {  
        this.javaVersion = javaVersion;  
    }  
    public String getJavaName() {  
        return javaName;  
    }  
    public void setJavaName(String javaName) {  
        this.javaName = javaName;  
    }  
    public String getJvmVersion() {  
        return jvmVersion;  
    }  
    public void setJvmVersion(String jvmVersion) {  
        this.jvmVersion = jvmVersion;  
    }  
    public String getJvmTotalMemory() {  
        return jvmTotalMemory;  
    }  
    public void setJvmTotalMemory(String jvmTotalMemory) {  
        this.jvmTotalMemory = jvmTotalMemory;  
    }  
    public String getJvmFreeMemory() {  
        return jvmFreeMemory;  
    }  
    public void setJvmFreeMemory(String jvmFreeMemory) {  
        this.jvmFreeMemory = jvmFreeMemory;  
    }  
    public String getDatabaseType() {  
        return databaseType;  
    }  
    public void setDatabaseType(String databaseType) {  
        this.databaseType = databaseType;  
    }  
    public String getDatabaseVersion() {  
        return databaseVersion;  
    }  
    public void setDatabaseVersion(String databaseVersion) {  
        this.databaseVersion = databaseVersion;  
    }  
    public String getDatabaseDriver() {  
        return databaseDriver;  
    }  
    public void setDatabaseDriver(String databaseDriver) {  
        this.databaseDriver = databaseDriver;  
    }  
    public String getDriverVersion() {  
        return driverVersion;  
    }  
    public void setDriverVersion(String driverVersion) {  
        this.driverVersion = driverVersion;  
    }  
    public String getJdbcUrl() {  
        return jdbcUrl;  
    }  
    public void setJdbcUrl(String jdbcUrl) {  
        this.jdbcUrl = jdbcUrl;  
    }  
      
    public Date getServerTime() {  
        return serverTime;  
    }  
    public void setServerTime(Date serverTime) {  
        this.serverTime = serverTime;  
    }  
    public String getJavaPath() {  
        return javaPath;  
    }  
    public void setJavaPath(String javaPath) {  
        this.javaPath = javaPath;  
    }  
    public int getCpuNum() {  
        return cpuNum;  
    }  
    public void setCpuNum(int cpuNum) {  
        this.cpuNum = cpuNum;  
    }  
    public List<CpuInfoVo> getCpuList() {  
        return cpuList;  
    }  
    public void setCpuList(List<CpuInfoVo> cpuList) {  
        this.cpuList = cpuList;  
    }  
    public int getNetNum() {  
        return netNum;  
    }  
    public void setNetNum(int netNum) {  
        this.netNum = netNum;  
    }  
    public List<NetInterfaceConfig> getNetList() {  
        return netList;  
    }  
    public void setNetList(List<NetInterfaceConfig> netList) {  
        this.netList = netList;  
    }  
    
    public String getMAC() {  
        return mac;  
    }  
    public void setMAC(String mac) {  
        this.mac = mac;  
    }  
    
    
    public Long getMemTotal() {  
        return memTotal;  
    }  
    public void setMemTotal(Long memTotal) {  
        this.memTotal = memTotal;  
    }  
    public Long getMemUsed() {  
        return memUsed;  
    }  
    public void setMemUsed(Long memUsed) {  
        this.memUsed = memUsed;  
    }  
    public Long getMemFree() {  
        return memFree;  
    }  
    public void setMemFree(Long memFree) {  
        this.memFree = memFree;  
    }
    public String getMemTotalFormat() {  
        return memTotalFormat;  
    }  
    public void setMemTotalFormat(String memTotalFormat) {  
        this.memTotalFormat = memTotalFormat;  
    }  
    public String getMemUsedFormat() {  
        return memUsedFormat;  
    }  
    public void setMemUsedFormat(String memUsedFormat) {  
        this.memUsedFormat = memUsedFormat;  
    }  
    public String getMemFreeFormat() {  
        return memFreeFormat;  
    }  
    public void setMemFreeFormat(String memFreeFormat) {  
        this.memFreeFormat = memFreeFormat;  
    }     
    
   
    
    
    
    public Long getSwapTotal() {  
        return swapTotal;  
    }  
    public void setSwapTotal(Long swapTotal) {  
        this.swapTotal = swapTotal;  
    }  
    public Long getSwapUsed() {  
        return swapUsed;  
    }  
    public void setSwapUsed(Long swapUsed) {  
        this.swapUsed = swapUsed;  
    }  
    public Long getSwapFree() {  
        return swapFree;  
    }  
    public void setSwapFree(Long swapFree) {  
        this.swapFree = swapFree;  
    }
    public String getSwapTotalFormat() {  
        return swapTotalFormat;  
    }  
    public void setSwapTotalFormat(String swapTotalFormat) {  
        this.swapTotalFormat = swapTotalFormat;  
    }  
    public String getSwapUsedFormat() {  
        return swapUsedFormat;  
    }  
    public void setSwapUsedFormat(String swapUsedFormat) {  
        this.swapUsedFormat = swapUsedFormat;  
    }  
    public String getSwapFreeFormat() {  
        return swapFreeFormat;  
    }  
    public void setSwapFreeFormat(String swapFreeFormat) {  
        this.swapFreeFormat = swapFreeFormat;  
    }  
    
    
    public Double getCpuFree() {  
        return cpuFree;  
    }  
    public void setCpuFree(Double cpuFree) {  
        this.cpuFree = cpuFree;  
    }   
    
    
    
    public String getCpuFreeFormat() {  
        return cpuFreeFormat;  
    }  
    public void setCpuFreeFormat(String cpuFreeFormat) {  
        this.cpuFreeFormat = cpuFreeFormat;  
    }   

      
    public Double getCpuUsed() {  
        return cpuUsed;  
    }  
    public void setCpuUsed(Double cpuUsed) {  
        this.cpuUsed = cpuUsed;  
    }  
     
    
    public String getCpuUsedFormat() {  
        return cpuUsedFormat;  
    }  
    public void setCpuUsedFormat(String cpuUsedFormat) {  
        this.cpuUsedFormat = cpuUsedFormat;  
    }  
     
    public List<HddInfoVo> getHddList() {  
        return hddList;  
    }  
    public void setHddList(List<HddInfoVo> hddList) {  
        this.hddList = hddList;  
    }   

} 