package cn.dunn.im.io;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarNotImplementedException;
import org.hyperic.sigar.Swap;

import com.sun.javafx.collections.MappingChange.Map;

public class SysInfo {

	public static void main(String[] args) throws Exception {
		SysInfo s = new SysInfo();
		// System.out.println("CPU个数："+s.getCpuCount());
		// s.getCpuTotal();
		// s.testCpuPerc(false);
		// s.getPhysicalMemory(false);
		// s.testWho();
		s.FileSystemInfo();

		// s.getEthernetInfo();
		// s.testGetOSInfo();
	}

	/**
	 * 1.CPU资源信息
	 */

	// a)CPU数量（单位：个）
	public static int getCpuCount() {
		Sigar sigar = new Sigar();
		int cpulength = 0;
		try {
			cpulength = sigar.getCpuInfoList().length;
			return cpulength;
		} catch (SigarException e) {
			e.printStackTrace();
		} finally {
			sigar.close();
		}
		return cpulength;
	}

	// b)CPU的总量（单位：HZ）及CPU的相关信息
	public static CpuInfo[] getCpuTotal() {
		Sigar sigar = new Sigar();
		CpuInfo[] infos = null;
		try {
			infos = sigar.getCpuInfoList();
			/*
			 * for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用 CpuInfo info =
			 * infos[i]; System.out.println("CPU的总量:" + info.getMhz());// CPU的总量MHz
			 * System.out.println("获得CPU的卖主：" + info.getVendor());// 获得CPU的卖主，如：Intel
			 * System.out.println("CPU的类别：" + info.getModel());// 获得CPU的类别，如：Celeron
			 * System.out.println("缓冲存储器数量：" + info.getCacheSize());// 缓冲存储器数量
			 * System.out.println("**************");
			 * 
			 * }
			 */
		} catch (SigarException e) {
			e.printStackTrace();
		} finally {
			sigar.close();
		}
		return infos;
	}

	// c)取当前操作系统的信息
	public static HashMap getSystemBaseInfo() {
		OperatingSystem OS = OperatingSystem.getInstance();
		HashMap hashmap = new HashMap();
		hashmap.put("osArch", OS.getArch());
		hashmap.put("osCpuEndian", OS.getCpuEndian());
		hashmap.put("osDataModel", OS.getDataModel());
		hashmap.put("osDescription", OS.getDescription());
		hashmap.put("osMachine", OS.getMachine());
		hashmap.put("osName", OS.getName());
		hashmap.put("osPatchLevel", OS.getPatchLevel());
		hashmap.put("osVendor", OS.getVendor());
		hashmap.put("osVendorCodeName", OS.getVendorCodeName());
		hashmap.put("osVendorName", OS.getVendorName());
		hashmap.put("osVendorVersion", OS.getVendorVersion());
		hashmap.put("osVersion", OS.getVersion());
		return hashmap;
	}

	// c)CPU的用户使用量、系统使用剩余量、总的剩余量、总的使用占用量等（单位：100%）
	public static ArrayList<HashMap> testCpuPerc(boolean is_format) {

		Sigar sigar = new Sigar();

		ArrayList<HashMap> list = new ArrayList<HashMap>();
		// 方式一，主要是针对一块CPU的情况
		CpuPerc cpu;
		try {
			cpu = sigar.getCpuPerc();
			list.add(printCpuPerc(cpu, is_format));
		} catch (SigarException e) {
			e.printStackTrace();
		}
		// 方式二，不管是单块CPU还是多CPU都适用
		CpuPerc cpuList[] = null;
		try {
			cpuList = sigar.getCpuPercList();
		} catch (SigarException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < cpuList.length; i++) {
			list.add(printCpuPerc(cpuList[i], is_format));
		}
		return list;

	}

	private static HashMap printCpuPerc(CpuPerc cpu, boolean is_format) {

		Double UserUsed = cpu.getUser();
		String SUserUsed = CpuPerc.format(UserUsed);
		Double SysUsed = cpu.getSys();
		String SSysUsed = CpuPerc.format(SysUsed);
		Double Wait = cpu.getWait();
		String SWait = CpuPerc.format(Wait);
		Double Nice = cpu.getNice();
		String SNice = CpuPerc.format(Nice);
		Double Free = cpu.getIdle();
		String SFree = CpuPerc.format(Free);
		Double TotalUsed = cpu.getCombined();
		String STotalUsed = CpuPerc.format(TotalUsed);

		OperatingSystem OS = OperatingSystem.getInstance();
		HashMap hashmap = new HashMap();

		if (is_format == true) {
			hashmap.put("UserUsed", SUserUsed);
			hashmap.put("SysUsed", SSysUsed);
			hashmap.put("Wait", SWait);
			hashmap.put("Nice", SNice);
			hashmap.put("Free", SFree);
			hashmap.put("TotalUsed", STotalUsed);
		} else {
			hashmap.put("UserUsed", UserUsed);
			hashmap.put("SysUsed", SysUsed);
			hashmap.put("Wait", Wait);
			hashmap.put("Nice", Nice);
			hashmap.put("Free", Free);
			hashmap.put("TotalUsed", TotalUsed);
		}
		return hashmap;
	}

	/**
	 * 2.内存资源信息
	 * 
	 */

	public static HashMap getPhysicalMemory(boolean is_format) {
		// a)物理内存信息
		DecimalFormat df = new DecimalFormat("#0.00");
		Sigar sigar = new Sigar();

		HashMap hashmap = new HashMap();
		try {
			Mem mem = sigar.getMem();
			Swap swap = sigar.getSwap();
			if (is_format) {
				hashmap.put("MemoryTotal", df.format((float) mem.getTotal() / 1024 / 1024 / 1024) + "G");
				hashmap.put("MemoryUsed", df.format((float) mem.getUsed() / 1024 / 1024 / 1024) + "G");
				hashmap.put("MemoryFree", df.format((float) mem.getFree() / 1024 / 1024 / 1024) + "G");
				hashmap.put("SwapTotal", df.format((float) swap.getFree() / 1024 / 1024 / 1024) + "G");
				hashmap.put("SwapUsed", df.format((float) swap.getUsed() / 1024 / 1024 / 1024) + "G");
				hashmap.put("SwapFree", df.format((float) swap.getFree() / 1024 / 1024 / 1024) + "G");
			} else {

				hashmap.put("MemoryTotal", mem.getTotal());
				hashmap.put("MemoryUsed", mem.getUsed());
				hashmap.put("MemoryFree", mem.getFree());
				hashmap.put("SwapTotal", swap.getFree());
				hashmap.put("SwapUsed", swap.getUsed());
				hashmap.put("SwapFree", swap.getFree());

			}

		} catch (SigarException e) {
			e.printStackTrace();
		} finally {
			sigar.close();
		}

		return hashmap;
	}

	/**
	 * 3.操作系统信息
	 * 
	 */

	// a)取到当前操作系统的名称：
	public static String getPlatformName() {
		String hostname = "";
		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (Exception exc) {
			Sigar sigar = new Sigar();
			try {
				hostname = sigar.getNetInfo().getHostName();
			} catch (SigarException e) {
				hostname = "localhost.unknown";
			} finally {
				sigar.close();
			}
		}
		return hostname;
	}

	// b)取当前操作系统的信息
	public static void testGetOSInfo() {
		OperatingSystem OS = OperatingSystem.getInstance();
		// 操作系统内核类型如： 386、486、586等x86
		System.out.println("OS.getArch() = " + OS.getArch());
		System.out.println("OS.getCpuEndian() = " + OS.getCpuEndian());//
		System.out.println("OS.getDataModel() = " + OS.getDataModel());//
		// 系统描述
		System.out.println("OS.getDescription() = " + OS.getDescription());
		System.out.println("OS.getMachine() = " + OS.getMachine());//
		// 操作系统类型
		System.out.println("OS.getName() = " + OS.getName());
		System.out.println("OS.getPatchLevel() = " + OS.getPatchLevel());//
		// 操作系统的卖主
		System.out.println("OS.getVendor() = " + OS.getVendor());
		// 卖主名称
		System.out.println("OS.getVendorCodeName() = " + OS.getVendorCodeName());
		// 操作系统名称
		System.out.println("OS.getVendorName() = " + OS.getVendorName());
		// 操作系统卖主类型
		System.out.println("OS.getVendorVersion() = " + OS.getVendorVersion());
		// 操作系统的版本号
		System.out.println("OS.getVersion() = " + OS.getVersion());
	}

	// c)取当前系统进程表中的用户信息
	public void testWho() {
		try {
			Sigar sigar = new Sigar();
			org.hyperic.sigar.Who[] who = sigar.getWhoList();
			if (who != null && who.length > 0) {
				for (int i = 0; i < who.length; i++) {
					System.out.println("\n~~~~~~~~~" + String.valueOf(i) + "~~~~~~~~~");
					org.hyperic.sigar.Who _who = who[i];
					System.out.println("获取设备getDevice() = " + _who.getDevice());
					System.out.println("获得主机getHost() = " + _who.getHost());
					System.out.println("获取的时间getTime() = " + _who.getTime());
					// 当前系统进程表中的用户名
					System.out.println("获取用户getUser() = " + _who.getUser());
				}
			}
		} catch (SigarException e) {
			e.printStackTrace();
		}
	}

	// 4.资源信息（主要是硬盘）
	// a)取硬盘已有的分区及其详细信息（通过sigar.getFileSystemList()来获得FileSystem列表对象，然后对其进行编历）：
	public static ArrayList<HashMap> FileSystemInfo() {
		Sigar sigar = new Sigar();
		ArrayList<HashMap> list = new ArrayList<HashMap>();

		FileSystem fslist[] = null;
		try {
			fslist = sigar.getFileSystemList();
		} catch (SigarException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < fslist.length; i++) {
			list.add(HddInfo(fslist[i], sigar));
		}
		return list;
	}

	private static HashMap HddInfo(FileSystem fs, Sigar sigar) {

		HashMap hddmap = new HashMap();
		DecimalFormat df = new DecimalFormat("#0.00");

		// 分区的盘符名称
		hddmap.put("DevName", fs.getDevName());
		// 分区的盘符名称
		hddmap.put("DirName", fs.getDirName());
		hddmap.put("Flags", fs.getFlags());
		// 文件系统类型，比如 FAT32、NTFS
		hddmap.put("SysTypeName", fs.getSysTypeName());
		// 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
		hddmap.put("TypeName", fs.getTypeName());
		// 文件系统类型
		hddmap.put("TypeNumber", fs.getType());

		if (fs.getType() == 2) {
			FileSystemUsage usage = null;
			try {
				usage = sigar.getFileSystemUsage(fs.getDirName());
			} catch (SigarException e) {
				e.printStackTrace();
			}
			// 文件系统总大小
			hddmap.put("Total", df.format((float) usage.getTotal() / 1024 / 1024) + "G");
			// 文件系统剩余大小
			hddmap.put("Free", df.format((float) usage.getFree() / 1024 / 1024) + "G");
			// 文件系统可用大小
			hddmap.put("Avail", df.format((float) usage.getAvail() / 1024 / 1024) + "G");
			// 文件系统已经使用量
			hddmap.put("Used", df.format((float) usage.getUsed() / 1024 / 1024) + "G");
			double usePercent = usage.getUsePercent() * 100D;
			// 文件系统资源的利用率
			hddmap.put("Usage", df.format(usePercent) + "%");
			hddmap.put("DiskReads", usage.getDiskReads());
			hddmap.put("DiskWrites", usage.getDiskWrites());
		}
		return hddmap;
	}

	public void testFileSystemInfo() throws Exception {
		Sigar sigar = new Sigar();
		FileSystem fslist[] = sigar.getFileSystemList();
		DecimalFormat df = new DecimalFormat("#0.00");
		// String dir = System.getProperty("user.home");// 当前用户文件夹路径
		for (int i = 0; i < fslist.length; i++) {
			FileSystem fs = fslist[i];
			// 分区的盘符名称
			System.out.println("fs.getDevName() = " + fs.getDevName());
			// 分区的盘符名称
			System.out.println("fs.getDirName() = " + fs.getDirName());
			System.out.println("fs.getFlags() = " + fs.getFlags());//
			// 文件系统类型，比如 FAT32、NTFS
			System.out.println("fs.getSysTypeName() = " + fs.getSysTypeName());
			// 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
			System.out.println("fs.getTypeName() = " + fs.getTypeName());
			// 文件系统类型
			System.out.println("fs.getType() = " + fs.getType());
			FileSystemUsage usage = null;
			try {
				usage = sigar.getFileSystemUsage(fs.getDirName());
			} catch (SigarException e) {
				if (fs.getType() == 2)
					throw e;
				continue;
			}
			switch (fs.getType()) {
			case 0: // TYPE_UNKNOWN ：未知
				break;
			case 1: // TYPE_NONE
				break;
			case 2: // TYPE_LOCAL_DISK : 本地硬盘
				// 文件系统总大小
				System.out.println(" Total = " + df.format((float) usage.getTotal() / 1024 / 1024) + "G");
				// 文件系统剩余大小
				System.out.println(" Free = " + df.format((float) usage.getFree() / 1024 / 1024) + "G");
				// 文件系统可用大小
				System.out.println(" Avail = " + df.format((float) usage.getAvail() / 1024 / 1024) + "G");
				// 文件系统已经使用量
				System.out.println(" Used = " + df.format((float) usage.getUsed() / 1024 / 1024) + "G");
				double usePercent = usage.getUsePercent() * 100D;
				// 文件系统资源的利用率
				System.out.println(" Usage = " + df.format(usePercent) + "%");
				break;
			case 3:// TYPE_NETWORK ：网络
				break;
			case 4:// TYPE_RAM_DISK ：闪存
				break;
			case 5:// TYPE_CDROM ：光驱
				break;
			case 6:// TYPE_SWAP ：页面交换
				break;
			}
			System.out.println(" DiskReads = " + usage.getDiskReads());
			System.out.println(" DiskWrites = " + usage.getDiskWrites());
		}
		return;
	}

	// 5.网络信息
	// a)当前机器的正式域名
	public String getFQDN() {
		Sigar sigar = null;
		try {
			return InetAddress.getLocalHost().getCanonicalHostName();
		} catch (UnknownHostException e) {
			try {
				sigar = new Sigar();
				return sigar.getFQDN();
			} catch (SigarException ex) {
				return null;
			} finally {
				sigar.close();
			}
		}
	}

	// b)取到当前机器的IP地址
	public static String getDefaultIpAddress() {
		String address = null;
		try {
			address = InetAddress.getLocalHost().getHostAddress();
			// 没有出现异常而正常当取到的IP时，如果取到的不是网卡循回地址时就返回
			// 否则再通过Sigar工具包中的方法来获取
			if (!NetFlags.LOOPBACK_ADDRESS.equals(address)) {
				return address;
			}
		} catch (UnknownHostException e) {
			// hostname not in DNS or /etc/hosts
		}
		Sigar sigar = new Sigar();
		try {
			address = sigar.getNetInterfaceConfig().getAddress();
		} catch (SigarException e) {
			address = NetFlags.LOOPBACK_ADDRESS;
		} finally {
			sigar.close();
		}
		return address;
	}

	// c)取到当前机器的MAC地址
	public static String getMAC() {
		Sigar sigar = null;
		try {
			sigar = new Sigar();
			String[] ifaces = sigar.getNetInterfaceList();
			String hwaddr = null;
			for (int i = 0; i < ifaces.length; i++) {
				NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
				if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress()) || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
						|| NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
					continue;
				}
				/*
				 * 如果存在多张网卡包括虚拟机的网卡，默认只取第一张网卡的MAC地址，如果要返回所有的网卡（包括物理的和虚拟的）
				 * 则可以修改方法的返回类型为数组或Collection ，通过在for循环里取到的多个MAC地址。
				 */
				hwaddr = cfg.getHwaddr();
				break;
			}
			return hwaddr != null ? hwaddr : null;
		} catch (Exception e) {
			return null;
		} finally {
			if (sigar != null)
				sigar.close();
		}
	}

	// d)获取网络流量等信息
	public void testNetIfList() throws Exception {
		Sigar sigar = new Sigar();
		String ifNames[] = sigar.getNetInterfaceList();
		for (int i = 0; i < ifNames.length; i++) {
			String name = ifNames[i];
			NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
			print("\nname = " + name);// 网络设备名
			print("Address = " + ifconfig.getAddress());// IP地址
			print("Netmask = " + ifconfig.getNetmask());// 子网掩码
			if ((ifconfig.getFlags() & 1L) <= 0L) {
				print("!IFF_UP...skipping getNetInterfaceStat");
				continue;
			}
			try {
				NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
				print("RxPackets = " + ifstat.getRxPackets());// 接收的总包裹数
				print("TxPackets = " + ifstat.getTxPackets());// 发送的总包裹数
				print("RxBytes = " + ifstat.getRxBytes());// 接收到的总字节数
				print("TxBytes = " + ifstat.getTxBytes());// 发送的总字节数
				print("RxErrors = " + ifstat.getRxErrors());// 接收到的错误包数
				print("TxErrors = " + ifstat.getTxErrors());// 发送数据包时的错误数
				print("RxDropped = " + ifstat.getRxDropped());// 接收时丢弃的包数
				print("TxDropped = " + ifstat.getTxDropped());// 发送时丢弃的包数
			} catch (SigarNotImplementedException e) {
			} catch (SigarException e) {
				print(e.getMessage());
			}
		}
	}

	void print(String msg) {
		System.out.println(msg);
	}

	// e)一些其他的信息
	public void getEthernetInfo() {
		Sigar sigar = null;
		try {
			sigar = new Sigar();
			String[] ifaces = sigar.getNetInterfaceList();
			for (int i = 0; i < ifaces.length; i++) {
				NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
				if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress()) || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
						|| NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
					continue;
				}
				System.out.println("cfg.getAddress() = " + cfg.getAddress());// IP地址
				System.out.println("cfg.getBroadcast() = " + cfg.getBroadcast());// 网关广播地址
				System.out.println("cfg.getHwaddr() = " + cfg.getHwaddr());// 网卡MAC地址
				System.out.println("cfg.getNetmask() = " + cfg.getNetmask());// 子网掩码
				System.out.println("cfg.getDescription() = " + cfg.getDescription());// 网卡描述信息
				System.out.println("cfg.getType() = " + cfg.getType());//
				System.out.println("cfg.getDestination() = " + cfg.getDestination());
				System.out.println("cfg.getFlags() = " + cfg.getFlags());//
				System.out.println("cfg.getMetric() = " + cfg.getMetric());
				System.out.println("cfg.getMtu() = " + cfg.getMtu());
				System.out.println("cfg.getName() = " + cfg.getName());
				System.out.println();
			}
		} catch (Exception e) {
			System.out.println("Error while creating GUID" + e);
		} finally {
			if (sigar != null)
				sigar.close();
		}
	}

}