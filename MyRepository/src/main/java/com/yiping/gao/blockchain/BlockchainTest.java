package com.yiping.gao.blockchain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;

/**
 * @Author: 高一平
 * @Date: 2019/9/5 11:56
 * @Description: 区块链
 * ***********************************************************************************************************
 * 区块链就是一串或者是一系列区块的集合，类似于链表的概念，每个区块都指向于后面一个区块，然后顺序的连接在一起
 * 在区块链中的每一个区块都存放了很多很有价值的信息，主要包括三个部分：
 * 1、本区块的数字签名
 * 2、上一个区块的数字签名
 * 3、还有一切需要加密的数据（这些数据在比特币中就相当于是交易的信息，它是加密货币的本质）
 * 每个数字签名不但证明了自己是特有的一个区块，而且指向了前一个区块的来源，让所有的区块在链条中可以串起来
 * 而数据就是一些特定的信息，你可以按照业务逻辑来保存业务数据
 * <p>
 * 每一个区块不仅包含前一个区块的hash值，同时包含自身的一个hash值
 * 自身的hash值是通过之前的hash值和数据data通过hash计算出来的
 * 如果前一个区块的数据一旦被篡改了，那么前一个区块的hash值也会同样发生变化（因为数据也被计算在内）
 * 这样也就导致了所有后续的区块中的hash值发生变化
 * 所以计算和比对hash值会让我们检查到当前的区块链是否是有效的
 * 也就避免了数据被恶意篡改的可能性，因为篡改数据就会改变hash值并破坏整个区块链。
 * ***********************************************************************************************************
 * 检查区块链的完整性
 * 检验区块链是否被篡改，是否有效
 * 区块中信息若被篡改，hash值将会改变，会导致整个区块链的破裂，区块链就无效了
 * {@link BlockchainUtils}
 * 循环区块链中的所有区块并且比较hash值
 * 用来检查hash值是否是于计算出来的hash值相等，同时previousHash值是否和前一个区块的hash值相等
 * 任何区块链中区块的一丝一毫改变都会导致这个函数返回false，也就证明了区块链无效了
 * ************************************************************************************************************
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
 * ************************************************************************************************************
 **/
public class BlockchainTest {

    public static ArrayList<Block> blocks = new ArrayList<>();
    public static int difficulty = 5;

    /**
     * 区块链测试方法
     * 第一个块称为创世块，因为它是头区块，所以我们只需输入“0”作为前一个块的preHash
     *
     * @param args
     */
    public static void main(String[] args) {
        blocks.add(new Block("Hi im the first block", "0"));
        System.out.println("Trying to Mine block 1... ");
        blocks.get(0).mineBlock(difficulty);

        blocks.add(new Block("Yo im the second block", blocks.get(blocks.size() - 1).hash));
        System.out.println("Trying to Mine block 2... ");
        blocks.get(1).mineBlock(difficulty);

        blocks.add(new Block("Hey im the third block", blocks.get(blocks.size() - 1).hash));
        System.out.println("Trying to Mine block 3... ");
        blocks.get(2).mineBlock(difficulty);

        JSONArray array = (JSONArray) JSONArray.toJSON(blocks);
        String jsonString = JSON.toJSONString(array, true);
        System.out.println("\nBlockChain is Valid: " + BlockchainUtils.isChainValid(blocks));
        System.out.println("\nThe block chain: ");
        System.out.println(jsonString);
    }

}
