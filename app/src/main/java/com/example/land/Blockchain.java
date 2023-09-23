package com.example.land;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> blockchain;
    private List<Transaction> pendingTransactions;

    public Blockchain() {
        blockchain = new ArrayList<>();
        pendingTransactions = new ArrayList<>();
        // Create and add the genesis block (initial block)
        Block genesisBlock = createGenesisBlock();
        blockchain.add(genesisBlock);
    }

    private Block createGenesisBlock() {
        // Implement the creation of the genesis block here
        // Typically, the genesis block has predefined data
        return new Block(0, "0", System.currentTimeMillis(), new ArrayList<>(), "GENESIS_HASH");
    }

    public void addTransaction(Transaction transaction) {
        pendingTransactions.add(transaction);
    }

    public void mineNextBlock(String minerAddress) throws NoSuchAlgorithmException {
        Block previousBlock = blockchain.get(blockchain.size() - 1);
        int newIndex = previousBlock.getIndex() + 1;
        long newTimestamp = System.currentTimeMillis();

        // Create a new block with pending transactions
        Block newBlock = new Block(newIndex, previousBlock.getHash(), newTimestamp, pendingTransactions, null);

        // Perform PoW mining and set the mined hash
        String minedHash = mineBlock(newBlock);
        newBlock.setHash(minedHash);

        // Add the mined block to the blockchain
        blockchain.add(newBlock);

        // Clear pending transactions
        pendingTransactions.clear();
    }

    private String mineBlock(Block block) throws NoSuchAlgorithmException {
        int nonce = 0;
        String data = block.getIndex() + block.getPreviousHash() + block.getTimestamp() + block.getTransactions().toString();

        while (true) {
            String candidateHash = block.calculateHash() + nonce;

            // Check if the hash meets the PoW criteria (starts with zeros)
            if (candidateHash.substring(0, 4).equals("0000")) {
                return candidateHash;
            }

            nonce++;
        }
    }

    public List<Block> getBlockchain() {
        return blockchain;
    }

    public List<Transaction> getPendingTransactions() {
        return pendingTransactions;
    }
}

