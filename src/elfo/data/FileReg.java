package elfo.data;

import javax.xml.crypto.Data;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

public class FileReg<T extends DataBasic> {

    private T meta;
    public FileReg(T meta){
        loadMeta(meta);
    }

    public boolean loadFile(String uri) {
        try{
            String load = new String(Files.readAllBytes(Paths.get(uri)));
            meta.loadData(load,true);
            return true;
        } catch (IOException e) {
            System.out.printf("\nImpossivel carregar '%s'\n",meta.getName());
            return false;
        }
    }

    public boolean writeMeta(String uri){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(uri,false));
            writer.write(meta.toString());
            System.out.println(meta.toString());
            writer.close();
            return true;
        } catch (IOException e) {
            System.out.printf("\nImpossivel gravar!\n");
        }
        return false;
    }

    public boolean loadMeta(T meta){
        this.meta = meta;
        return true;
    }

}
