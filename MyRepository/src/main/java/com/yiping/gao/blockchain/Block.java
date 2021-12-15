package com.yiping.gao.blockchain;

import com.yiping.gao.common.utils.encrypt.EncryptUtils;
import lombok.Data;

/**
 * @Author: 高一平
 * @Date: 2019/9/5 14:56
 * @Description: 区块链区块
 * <p>
 * hash值应该包括区块中所有我们不希望被恶意篡改的数据
 * 在此包括preHash，data和timestamp
 * <p>
 * hash值由HA256算法生成
 * SHA256算法的hash值大小为256位
 * <p>
 * hash值用作表示大量数据的固定大小的唯一值
 * 之所以选用SHA256，是因为它的大小正合适
 * 一方面产生重复hash值的可能性很小
 * 另一方面在区块链实际应用过程中，有可能会产生大量的区块，而使得信息量很大，那么256位的大小就比较恰当了
 **/
@Data
public class Block {

    /**
     * 本区块的hash
     */
    public String hash;
    /**
     * 上一个区块的hash
     */
    public String preHash;
    /**
     * 区块保存的信息
     */
    private String data;
    private long timestamp;
    private int nonce;

    public Block(String data, String preHash) {
        this.data = data;
        this.preHash = preHash;
        this.timestamp = System.currentTimeMillis();
        this.hash = getHash();
    }

    public String getHash() {
        String hash = EncryptUtils.encryptBySHA256(preHash + timestamp + nonce + data);
        return hash;
    }

    /**
     * 为防止有人恶意来篡改之前的数据，然后创建一条更长的区块链并全网发布呈现在网络中
     * 引入了一个int值称为difficulty难度
     * 低的难度比如1和2，普通的电脑基本都可以马上计算出来
     * 我的建议是在4-6之间进行测试，普通电脑大概会花费3秒时间
     * 在莱特币中难度大概围绕在442592左右，而在比特币中每一次挖矿都要求大概在10分钟左右，当然根据所有网络中的计算能力，难度也会不断的进行修改。
     * 提高difficulty难度，如果有人在你的区块链系统中恶意篡改数据：
     * 1、他们的区块链是无效的。
     * 2、他们无法创建更长的区块链
     * 网络中诚实的区块链会在长链中更有时间的优势
     * 因为篡改的区块链将无法赶上长链和有效链
     * 除非他们比你网络中所有的节点拥有更大的计算速度，可能是未来的量子计算机或者是其他什么。
     *
     *
     * @param difficulty
     */
    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = getHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }
}
