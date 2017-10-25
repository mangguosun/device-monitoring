package cn.dunn.im.io;

public class CpuInfoVo {

	int cpumhz;
	String cpuidle;
	String cpucombined;	
	String cpuvendor;	
	String cpumodel;	
	public void setCpuMhz(int cpumhz) {
		
		this.cpumhz = cpumhz;
	}
	public int getCpuMhz() {
		return cpumhz;
	}	
	public void setCpuLdle(String cpuidle) {
		
		this.cpuidle = cpuidle;
	}	
	public String getCpuLdle() {
		return cpuidle;
	}	
	public void setCpuCombined(String cpucombined) {
		
		this.cpucombined = cpucombined;
	}	
	public String getCpuCombined() {
		return cpucombined;
	}
	
	public void setVendor(String cpuvendor) {	
		this.cpuvendor = cpuvendor;
	}		
	public String getVendor() {
		return cpuvendor;
	}	
	public void setModel(String cpumodel) {	
		this.cpumodel = cpumodel;
	}		
	public String getModel() {
		return cpumodel;
	}	
		
	
}
