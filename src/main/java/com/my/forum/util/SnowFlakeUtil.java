package com.my.forum.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 53 bits unique id:
 * <p>
 * |--------|--------|--------|--------|--------|--------|--------|--------|
 * |00000000|00011111|11111111|11111111|11111111|11111111|11111111|11111111|
 * |--------|---xxxxx|xxxxxxxx|xxxxxxxx|xxxxxxxx|xxx-----|--------|--------|
 * |--------|--------|--------|--------|--------|---xxxxx|xxxxxxxx|xxx-----|
 * |--------|--------|--------|--------|--------|--------|--------|---xxxxx|
 * <p>
 * Maximum ID = 11111_11111111_11111111_11111111_11111111_11111111_11111111
 * <p>
 * Maximum TS = 11111_11111111_11111111_11111111_111
 * <p>
 * Maximum NT = ----- -------- -------- -------- ---11111_11111111_111 = 65535
 * <p>
 * Maximum SH = ----- -------- -------- -------- -------- -------- ---11111 = 31
 * <p>
 * It can generate 64k unique id per IP and up to 2106-02-07T06:28:15Z.
 * <p>
 * 改良版雪花算法，可解决超长的ID导致传到JS精度丢失的问题
 */
public class SnowFlakeUtil {
    private SnowFlakeUtil() {
    }

    private static final Logger logger = LoggerFactory.getLogger(SnowFlakeUtil.class);

    private static final Pattern PATTERN_HOSTNAME = Pattern.compile("^.*\\D+([0-9]+)$");

    /**
     * 起始时间戳(秒) 2021-08-01 00:00:00
     */
    private static final long START_TIME = 1627747200;
    /**
     * 递增占用的空间 2个字节（65535）
     */
    private static final long MAX_NEXT = 0b11111_11111111_111L;
    /**
     * 分片ID（机器ID）
     */
    private static final long SHARD_ID = getServerIdAsLong();
    /**
     * 递增偏移
     */
    private static long offset = 0;
    /**
     * 最后生成的时间（秒）
     */
    private static long lastEpoch = 0;

    public static long nextId() {
        return nextId(System.currentTimeMillis() / 1000);
    }

    private static synchronized long nextId(long epochSecond) {
        if (epochSecond < lastEpoch) {
            logger.warn("clock is back: {} from previous: {}", epochSecond, lastEpoch);
            epochSecond = lastEpoch;
        }
        if (lastEpoch != epochSecond) {
            lastEpoch = epochSecond;
            offset = 0;
        }
        offset++;
        long next = offset & MAX_NEXT;
        if (next == 0) {
            logger.warn("maximum id reached in 1 second in epoch: {}", epochSecond);
            return nextId(epochSecond + 1);
        }
        return generateId(epochSecond, next);
    }

    private static long generateId(long epochSecond, long next) {
        return ((epochSecond - START_TIME) << 21) | (next << 5) | SHARD_ID;
    }

    private static long getServerIdAsLong() {
        try {
            String hostname = InetAddress.getLocalHost().getHostName();
            Matcher matcher = PATTERN_HOSTNAME.matcher(hostname);
            if (matcher.matches()) {
                long n = Long.parseLong(matcher.group(1));
                if (n >= 0 && n < 8) {
                    logger.info("detect server id from host name {}: {}.", hostname, n);
                    return n;
                }
            }
        } catch (UnknownHostException e) {
            logger.warn("unable to get host name. set server id = 0.");
        }
        return 0;
    }
}
