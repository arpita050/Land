package com.example.land;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Block {
    private int index;
    private String previousHash;
    private long timestamp;
    private List<Transaction> transactions;
    private String hash;

    public Block(int index, String previousHash, long timestamp, List<Transaction> transactions, String hash) {
        this.index = index;
        this.previousHash = previousHash;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.hash = hash;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    // Method to calculate the block's hash
    public String calculateHash() throws NoSuchAlgorithmException {
        // Implement your hash calculation logic (e.g., using CryptoUtils)
        return CryptoUtils.calculateHash(index + previousHash + timestamp + transactions.toString());
    }
}
