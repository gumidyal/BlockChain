import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.ByteBuffer;

public class Block {
        private int num;
        private int data;
        private Hash prevHash;
        private long nonce = -1;
        private Hash hash;

        public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
               this.num = num;
               this.data = amount;
               this.prevHash = prevHash;

               //Generate hash
               boolean nonceFound = false;
               long nonce = -1;

               while (this.nonce == -1) {
                       nonce++;
                       Hash hash = new Hash(Block.calculateHash(num, amount, prevHash, nonce));
                       if (hash.isValid()) {
                             this.nonce = nonce; 
                             this.hash = hash;
                       }
               }
        }

        public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
                this.num = num;
                this.data = amount;
                this.prevHash = prevHash;
                this.nonce = nonce;

                //Generate hash
                this.hash = new Hash(Block.calculateHash(num, amount, prevHash, nonce));
        }

        public int getNum() {
                return this.num;
        }

        public int getAmount() {
                return this.data;
        }

        public long getNonce() {
                return this.nonce;
        }

        public Hash getPrevHash() {
                return this.prevHash;
        }

        public Hash getHash() {
                return this.hash;
        }

        public String toString() {
                //Implement
        }

        public static byte[] calculateHash(int num, int data, long nonce) throws NoSuchAlgorithmException {
                MessageDigest md = MessageDigest.getInstance("sha-256");

                md.update(ByteBuffer.allocate(4).putInt(num).array());
                md.update(ByteBuffer.allocate(4).putInt(data).array());
                md.update(ByteBuffer.allocate(4).putLong(nonce).array());

                return md.digest();
        }

        public static byte[] calculateHash(int num, int data, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
                MessageDigest md = MessageDigest.getInstance("sha-256");

                md.update(ByteBuffer.allocate(4).putInt(num).array());
                md.update(ByteBuffer.allocate(4).putInt(data).array());
                md.update(prevHash.getData());
                md.update(ByteBuffer.allocate(4).putLong(nonce).array());

                return md.digest();
        }
}
