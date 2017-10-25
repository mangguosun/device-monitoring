package cn.dunn.im.io;

public class HddInfoVo {

	// 分区的盘符名称
	String devname;
	// 分区的盘符名称
	String dirname;	
	// 文件系统类型，比如 FAT32、NTFS
	Long flags;	
	// 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
	String systypename;	
	String typename;	
	// 文件系统类型
	int typenumber;	
	
	// 文件系统总大小
	String total;	
	// 文件系统剩余大小
	String free;
	// 文件系统可用大小
	String avail;
	// 文件系统已经使用量
	String used;
	// 文件系统资源的利用率
	String usage;
	Long diskreads;
	Long diskwrites;
	
	
	
	public String getDevName() {
		return  devname;
	}		
	
	public void setDevName(String devname) {
		this.devname = devname;
	}	
	
	public String getDirName() {
		return  dirname;
	}		
	
	public void setDirName(String dirname) {
		this.dirname = dirname;
	}		
	
	
	public Long getFlags() {
		return  flags;
	}		
	
	public void setFlags(Long flags) {
		this.flags = flags;
	}	
	
	public String getSysTypeName() {
		return  systypename;
	}		
	
	public void setSysTypeName(String systypename) {
		this.systypename = systypename;
	}	
		
	public String getTypeName() {
		return  typename;
	}		
	
	public void setTypeName(String typename) {
		this.typename = typename;
	}	
	public int getTypeNumber() {
		return  typenumber;
	}		
	
	public void setTypeNumber(int typenumber) {
		this.typenumber = typenumber;
	}
	
	public String getTotal() {
		return  total;
	}		
	
	public void setTotal(String total) {
		this.total = total;
	}
	
	
	public String getFree() {
		return  free;
	}		
	
	public void setFree(String free) {
		this.free = free;
	}
		
	
	public String getAvail() {
		return  avail;
	}		
	
	public void setAvail(String avail) {
		this.avail = avail;
	}
	public String getUsed() {
		return  used;
	}	
	public void setUsed(String used) {
		this.used = used;
	}	
	
	public String getUsage() {
		return  usage;
	}	
	public void setUsage(String usage) {
		this.usage = usage;
	}		
	
	
	public Long getDiskReads() {
		return  diskreads;
	}	
	public void setDiskReads(Long diskreads) {
		this.diskreads = diskreads;
	}		
	public Long getDiskWrites() {
		return  diskwrites;
	}	
	public void setDiskWrites(Long diskwrites) {
		this.diskwrites = diskwrites;
	}		
	
}
