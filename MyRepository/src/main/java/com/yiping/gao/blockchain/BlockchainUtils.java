package com.yiping.gao.blockchain;

import java.util.List;

/**
 * @Author: 高一平
 * @Date: 2019/9/5 20:26
 * @Description: 区块链相关工具
 **/
public class BlockchainUtils {

    /**
     * 检查区块链的完整性
     * 检验区块链是否被篡改，是否有效
     * 区块中信息若被篡改，hash值将会改变，会导致整个区块链的破裂，区块链就无效了
     *
     * 循环区块链中的所有区块并且比较hash值
     * 用来检查hash值是否是于计算出来的hash值相等，同时previousHash值是否和前一个区块的hash值相等
     * 任何区块链中区块的一丝一毫改变都会导致这个函数返回false，也就证明了区块链无效了
     *
     * @param blockchain
     * @return
     */
    public static Boolean isChainValid(List<Block> blockchain) {
        Block currentBlock;
        Block preBlock;

        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            preBlock = blockchain.get(i - 1);
            if (!currentBlock.hash.equals(currentBlock.getHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }
            if (!preBlock.hash.equals(currentBlock.preHash)) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
        }
        return true;
    }

}
