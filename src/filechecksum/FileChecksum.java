package filechecksum;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 *
 * @author oscar
 */
public class FileChecksum {
    public static byte[] obtenerChecksum(String nombreArchivo) throws Exception {
        InputStream fis = new FileInputStream(nombreArchivo);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;
        // Leer el archivo por bloques (el metodo DIGEST trabaja con bytes)
        do {
            // Leer datos y ponerlos dentro del búfer
            numRead = fis.read(buffer);
            // Si se leyó algo, se actualiza el MessageDigest
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        // Devolver el array de bytes con la funcion hash
        return complete.digest();
    }

    public static String obtenerMD5ComoString(String nombreArchivo) throws Exception {
        // Convertir el array de bytes a cadena (para poder mostrarlo por pantalla)
        byte[] b = obtenerChecksum(nombreArchivo);
        StringBuilder resultado = new StringBuilder();

        for (byte unByte : b) {
            resultado.append(Integer.toString((unByte & 0xff) + 0x100, 16).substring(1));
        }
        return resultado.toString();
    }

    public static void main(String args[]) {
        // El nombre de archivo del que vas a obtener la suma de verificación MD5
        // IMPORTANTE: Escapar las diagonales de la ruta
        String nombreArchivo = "C:\\Users\\oscar\\Downloads\\vlc-3.0.18-win64.exe";
        try {
            String checksum = obtenerMD5ComoString(nombreArchivo);
            System.out.println("El MD5 checksum de " + nombreArchivo + " es " + checksum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
