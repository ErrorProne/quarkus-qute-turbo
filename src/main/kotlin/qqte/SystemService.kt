package qqte

import com.sun.management.OperatingSystemMXBean
import java.lang.management.ManagementFactory
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SystemService {
    fun getCpuLoad(): Int {
        return (ManagementFactory.getPlatformMXBean(
            OperatingSystemMXBean::class.java
        ).cpuLoad * 100).toInt()
    }

    fun getFreeMem(): Long {
        return ManagementFactory.getPlatformMXBean(
            OperatingSystemMXBean::class.java
        ).freeMemorySize / 1024 / 1024
    }

    fun getOs(): String? {
        return ManagementFactory.getPlatformMXBean(
            OperatingSystemMXBean::class.java
        ).name
    }
}
