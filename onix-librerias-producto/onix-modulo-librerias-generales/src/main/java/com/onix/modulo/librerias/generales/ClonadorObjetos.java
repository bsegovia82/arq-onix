package com.onix.modulo.librerias.generales;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ClonadorObjetos 
{
  
	public static <T extends Serializable> T clonarObjetosPorBytes(T objeto,  Class<T> type) throws Throwable
	{
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		ByteArrayInputStream bin = null;
		ObjectInputStream ois = null;
		try {

			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos); 
			oos.writeObject(objeto); 
			oos.flush();
			bin = new ByteArrayInputStream(bos.toByteArray());

			ois = new ObjectInputStream(bin);

			return type.cast(ois.readObject());

			}finally
		{
				if ( bos != null)
				{
					bos.close();
				}
				if ( oos != null)
				{
					oos.close();
				}
				if ( bin != null)
				{
					bin.close();
				}
				if ( ois != null)
				{
					ois.close();
				}
		}
	}
	
}
