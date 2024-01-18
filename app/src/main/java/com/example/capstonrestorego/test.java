package com.example.capstonrestorego;

import android.widget.EditText;
import com.example.capstonrestorego.Model.Json;
import com.example.capstonrestorego.Model.Post;
import com.klaytn.caver.Caver;
import com.klaytn.caver.abi.datatypes.DynamicArray;
import com.klaytn.caver.abi.datatypes.DynamicBytes;
import com.klaytn.caver.abi.datatypes.Type;
import com.klaytn.caver.contract.Contract;
import com.klaytn.caver.contract.SendOptions;
import com.klaytn.caver.methods.request.KlayLogFilter;
import com.klaytn.caver.methods.response.KlayLogs;
import com.klaytn.caver.wallet.keyring.SingleKeyring;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.exceptions.TransactionException;
import xyz.groundx.caver_ext_kas.CaverExtKAS;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.ApiException;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.wallet.model.Account;
import xyz.groundx.caver_ext_kas.rest_client.io.swagger.client.api.wallet.model.Accounts;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class test {

    private static final String byteCode = "60556023600b82828239805160001a607314601657fe5b30600052607381538281f3fe73000000000000000000000000000000000000000030146080604052600080fdfea265627a7a723158200660728a5900e6186b9feb89a478d230e28b6dab3ba3a5d8ca5a97fb55101cc164736f6c63430005110032";
    static EditText description, location, fileName = null;
    static String baourl = "https://api.baobab.klaytn.net:8651";
    private static Post post;
    private static Json abijon = new Json();

    public static void signTransact() {
        Caver caver = new Caver(baourl);
        String contractAddress = "0x819660a4d803ff6235538e312d322fe6dd8af562";
        SingleKeyring feePayer = caver.wallet.keyring.create(contractAddress, "pvkey");
        caver.wallet.add(feePayer);


        try {
            String photodata = "0xffd8ffe000104a46494600010100000100010000ffe202284943435f50524f46494c450001010000021800000000021000006d6e74725247422058595a2000000000000000000000000061637370000000000000000000000000000000000000000000000000000000010000f6d6000100000000d32d0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000964657363000000f0000000747258595a00000164000000146758595a00000178000000146258595a0000018c0000001472545243000001a00000002867545243000001a00000002862545243000001a00000002877747074000001c80000001463707274000001dc0000003c6d6c756300000000000000010000000c656e5553000000580000001c0073005200470042000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000058595a200000000000006fa2000038f50000039058595a2000000000000062990000b785000018da58595a2000000000000024a000000f840000b6cf706172610000000000040000000266660000f2a700000d59000013d000000a5b000000000000000058595a20000000000000f6d6000100000000d32d6d6c756300000000000000010000000c656e5553000000200000001c0047006f006f0067006c006500200049006e0063002e00200032003000310036ffdb004300ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffdb004301ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffc00011080105018403012200021101031101ffc40017000101010100000000000000000000000000010203ffc40017100101010100000000000000000000000000110131ffc4001501010100000000000000000000000000000001ffc40014110100000000000000000000000000000000ffda000c03010002110311003f000a020a80000000008000000000000000000000000000000000000000000000000000000000000280a00000020a020a880000000000000000000000000000000000000000000000000000000080a200d00a00000000000008a020a880028000008000000000000000000000000000000000000000000000280a0000000000000080a200a0020a20828a2008000000000000000000000000000000000000000000280a00000000000000000022800000200a200000008000000000000000000000000000000000000280a00000000000000000000000000008a800080028008000000000000000000000000000000000280a00000000000000000000000000000008a020a0200000800000000000000000000000000020a0280a00000000000000000000000000000000000020a80000008000000000000000000000000000280a00000000000000000000000000008a0000008000000800000000000000280080028000008000280a000000000000000002000a00000000000000008a020000000000000080028000000000008000280000a000002000a000000000000000000002000082802000000028000000000000000000000000a02000000000000000000a000000000000000002008000000280080028000000000000000000000a02000000000000000000000000000000000000000020280000000000000000000000000802880371234032340322c2020b122000000000000000000000000000008280000000000000000000000000202a000003a000000000000000908a02448d00c8d00c8b08082c4800220a200a2000828a200a200a200a200a200a200000000000000000e800000000000000000000000000000000242280cc4dc6d106000005000000010051005100510051140000000001d000000000000000000000000000000000000137800c00800280000008000000000000000000a00000ffd9";
            Contract contract = caver.contract.create(abijon.getABIjson(), "0x865df85ddfc3ebe3647bac58c6ccb61d2c8e7858");
            SendOptions sendOptions = new SendOptions();
            sendOptions.setFrom(feePayer.getAddress());
            sendOptions.setGas(BigInteger.valueOf(100000000));
            sendOptions.setFeePayer(feePayer.getAddress());
            sendOptions.setFeeDelegation(true);
            contract.send(sendOptions, "uploadPhoto", photodata, "sdf", "destination", "location");


        } catch (IOException | TransactionException | ClassNotFoundException | NoSuchMethodException |
                 InvocationTargetException | InstantiationException | IllegalAccessException e) {
            // 예외 처리
        }
    }

    public static String byteArrayToHex(byte[] byteArray) {
        if (byteArray == null || byteArray.length == 0) {
            return null;
        }
        StringBuilder stringBuffer = new StringBuilder(byteArray.length * 2);
        String hexNumber;
        for (byte aBa : byteArray) {
            hexNumber = "0" + Integer.toHexString(0xff & aBa);
            stringBuffer.append(hexNumber.substring(hexNumber.length() - 2));
        }
        return stringBuffer.toString();
    }

    public static byte[] convertObjectToBytes(Object obj) throws IOException {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(obj);
            System.out.println(boas.toByteArray());
            byte boy[] = boas.toByteArray();
            for (int i = 0; i < boy.length; i++) {
                System.out.print(boy[i]);
            }

            return boas.toByteArray();
        }
    }


    private static void pathm() {
        Caver caver = new Caver(baourl);
        String contractAddress = "0x819660a4d803ff6235538e312d322fe6dd8af562";
        SingleKeyring feePayer = caver.wallet.keyring.create(contractAddress, "pvkey");
        caver.wallet.add(feePayer);


        try {


            Contract contract = caver.contract.create(abijon.getABIjson(), "0xb67a16850c8495033e906c7dfd88d6d363db0905");
            KlayLogFilter filter = new KlayLogFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, "0xb67a16850c8495033e906c7dfd88d6d363db0905", null);
            KlayLogs logs = contract.getPastEvent("Transfer", filter);


            System.out.println(logs);


            List<KlayLogs.LogResult> logResults = logs.getResult();
            KlayLogs.LogObject logObject = (KlayLogs.LogObject) logResults.get(logResults.size() - 1);
            KlayLogs.Log log = logObject.get();
            System.out.println(logObject);
            System.out.println(logResults.size());

            log.getTopics().get(3).getBytes();//TOKENID transfer 이벤트 리턴 4가지 원소에서 4번째 tokenid 추출

            List<Type> output = contract.call("getPhoto", log.getTopics().get(3));


            for (int i = 0; i < output.size(); i++) {
                System.out.println(
                        output.get(i).getValue());
            }

            byte[] photobyte = ((DynamicBytes) ((ArrayList) output).get(2)).getValue();
            List man = ((DynamicArray) ((ArrayList) output).get(1)).getValue();
            System.out.println(man.get(man.size() - 1));


        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {

        }

    }

    public static void createAccount() throws ApiException {
        CaverExtKAS caver = new CaverExtKAS();
        String accessKeyId = "KASKD9KL8U3ZZ952PD63RK4V";
        String secretAccessKey = "pvkey";
        caver.initKASAPI(1001, accessKeyId, secretAccessKey);
        Account account = caver.kas.wallet.createAccount();
        System.out.println(account);
        String fpvkey = account.getKeyId().substring(account.getKeyId().lastIndexOf(":"));
        System.out.println(fpvkey.substring(1));

    }

    public static void approvetest() {
        try {
            String accessKey = "KASKD9KL8U3ZZ952PD63RK4V";
            String secretAccessKey = "pvkey";
            CaverExtKAS caver = new CaverExtKAS(1001, accessKey, secretAccessKey);
            String contractAddress = "0xb67a16850c8495033e906c7dfd88d6d363db0905";
            SendOptions sendOptions1 = new SendOptions("0x55772B1eBADf827068E4C68B6986d6C9Dadef072", BigInteger.valueOf(50000000));
            Contract sampleContract = new Contract(caver, abijon.getABIjson(), contractAddress);
            sampleContract.send(sendOptions1, "approve", "0xe2f5b85d20A36832731B815aF5Ad33e29767A7b1", 250);
            sampleContract.send(sendOptions1, "transferOwnership", 250, "0xe2f5b85d20A36832731B815aF5Ad33e29767A7b1");

        } catch (IOException | TransactionException | ClassNotFoundException | NoSuchMethodException |
                 InvocationTargetException | InstantiationException | IllegalAccessException e) {

        }

    }


    public static void main(String[] args) throws ApiException {
        CaverExtKAS caver = new CaverExtKAS();

        String accessKeyId = "KASKD9KL8U3ZZ952PD63RK4V";
        String secretAccessKey = "";
        caver.initKASAPI(1001, accessKeyId, secretAccessKey);
        Accounts accounts = caver.kas.wallet.getAccountList();

        System.out.println(accounts);


    }

}





