package elfoAPI.data;

import elfoAPI.exception.data.DataCannotBeAccessedException;

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


    public Object open(String uri) throws DataCannotBeAccessedException {
        //tenta abrir arquivo
        try {
            FileInputStream fileInput = new FileInputStream(uri);
            ObjectInputStream inputStream = new ObjectInputStream(fileInput);
            Object out = inputStream.readObject();
            inputStream.close();
            fileInput.close();
            return out;
        } catch (IOException | ClassNotFoundException e){
            throw new DataCannotBeAccessedException(uri, e.getMessage());
        }
    }

    public void save(String uri, Serializable meta) throws DataCannotBeAccessedException {
        FileOutputStream fileOutput = null;
        ObjectOutputStream outputStream = null;
        try {
            fileOutput = new FileOutputStream(uri);
            outputStream = new ObjectOutputStream(fileOutput);
            outputStream.writeObject(meta);
            outputStream.close();
            fileOutput.close();
        } catch (IOException e) {
            throw new DataCannotBeAccessedException(uri,e.getLocalizedMessage());
        } finally {
            if(fileOutput != null){
                try{
                    fileOutput.close();
                } catch (IOException e) {
                    throw new DataCannotBeAccessedException(uri,e.getLocalizedMessage());
                }
            }
            if(fileOutput != null){
                try {
                    fileOutput.close();
                } catch (IOException e) {
                    throw new DataCannotBeAccessedException(uri,e.getLocalizedMessage());
                }
            }
        }

    }
}
