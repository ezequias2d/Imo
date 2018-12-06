package elfo.data;

import java.io.*;

public class Serializer{

    private static Serializer serializerInstance;

    private Serializer(){
    }

    public static Serializer getInstance(){
        if(serializerInstance == null){
            serializerInstance = new Serializer();
        }
        return serializerInstance;
    }
    public Object open(String uri) throws IOException, ClassNotFoundException {
        //tenta abrir arquivo
        FileInputStream fileInput = new FileInputStream(uri);
        ObjectInputStream inputStream = new ObjectInputStream(fileInput);
        Object out = inputStream.readObject();
        inputStream.close();
        fileInput.close();
        return out;
    }

    public void save(String uri, Serializable meta) throws IOException  {
        //tenta salvar arquivo
        FileOutputStream fileOutput = new FileOutputStream(uri);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutput);
        //salva objeto
        outputStream.writeObject(meta);
        //fecha
        outputStream.close();
        fileOutput.close();
    }
}
