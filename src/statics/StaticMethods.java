package statics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Iterator;
import java.util.Vector;

import java.net.URL;

import mainfolder.MainMenuFrame;
import album.Album;
import album.ProtectedAlbum;

public class StaticMethods {

private static Vector<Album> album_list;
	
	/**
	 * Dato in input un vettore di files elimina dal vettore di file immagine
	 * di ogni album eventuali cloni dei file dati in ingresso
	 * 
	 * @param selectedFiles
	 */
	static public void RemoveClonesFile(Vector<File> selectedFiles)
	{
		/**
		 * Il primo for scorre i file di cui rimuovere i cloni
		 */
		album_list = MainMenuFrame.GetAlbumList();
		for(Iterator<File> iter1 = selectedFiles.iterator(); iter1.hasNext();)
		{
			File imgF = iter1.next();
			/**
			 * 'imgF' contiene l'immagine di cui sta cercando i cloni
			 * dopo avvia un secondo for che scorre la lista di album
			 */
			for(Iterator<Album> iter2 = album_list.iterator(); iter2.hasNext();)
			{
				Album alb = iter2.next();
				Vector<File> album_files = alb.GetPicsFiles();
				
				/**
				 * 'alb' e l'album all'interno del quale cerco i cloni del file 'img_f' 
				 * mentre 'album_files' contiene la lista dei suoi file immagine, 
				 * successivamente avvia un terzo for su questi ultimi (file immagine dell'album 'alb')
				 */
				for(Iterator<File> iter3 = album_files.iterator(); iter3.hasNext();)
				{
					File alb_f = iter3.next();
					if(imgF.equals(alb_f))
					{
						if(!(alb instanceof ProtectedAlbum))
						{
							iter3.remove();	
							alb.getCaller().RefreshPrev();
						}
						else
							iter1.remove();
					}
					
					/**
					 * Inserisce volta per volta uno dei file in 'alb_f' e lo confronta con il file iniziale 'img_f' 
					 * se i due file coincidono 
					 * rimuove il file clone dall'album e aggiorna l'anteprima dell'album da cui il clone e stato rimosso
					 */
				}
			}
		}
		
	}
	
	
	/**
	 * Uguale a 'RemoveClonesFile' ma opera sugli URLs invece che sui Files
	 */
	
	static public void RemoveClonesUrl(Vector<URL> selected_urls)
	{
		album_list = MainMenuFrame.GetAlbumList();
		for(Iterator<URL> iter1 = selected_urls.iterator(); iter1.hasNext();)
		{
			URL img_u = iter1.next();
			
			for(Iterator<Album> iter2 = album_list.iterator(); iter2.hasNext();)
			{
				Album alb = iter2.next();
				Vector<URL> album_urls = alb.GetPicsUrls();
				
				for(Iterator<URL> iter3 = album_urls.iterator(); iter3.hasNext();)
				{
					URL alb_u = iter3.next();
					//System.out.println(alb_u.getPath());
					//System.out.println(img_u.getPath());
					if(img_u.getPath().equals(alb_u.getPath()))
						iter3.remove();
				}
			}
		}
	}
	
	/**
	 * Effettua il salvataggio su file dello stato attuale della galleria
	 * 
	 * @param name
	 * @param date
	 * @return
	 */
	static public boolean Save(String name, String date)
	{
		FileOutputStream save_out = null;
		try {
			save_out = new FileOutputStream(Constants.saveFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(save_out == null)
			return false;
		
		ObjectOutputStream saveobj_out = null;
		try {
			saveobj_out = new ObjectOutputStream(save_out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(saveobj_out == null)
		{
			return false;
		}
		
		try {
			saveobj_out.writeObject(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			saveobj_out.writeObject(date);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			saveobj_out.writeObject(MainMenuFrame.GetAlbumList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			saveobj_out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	/**
	 * Effettua il caricamento da file dello stato salvato della galleria
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	static public boolean Load()
	{
		if(!Constants.saveFile.exists())
			return false;
		
		
		String name = "NoName";
		String date = "99/99/9999";
		Vector<Album> album_list = null;
		FileInputStream save_in = null;
		try {
			save_in = new FileInputStream(Constants.saveFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(save_in == null)
		{
			return false;
		}
		
		ObjectInputStream saveobj_in = null;
		try {
			saveobj_in = new ObjectInputStream(save_in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(saveobj_in == null)
		{
			return false;
		}
		
		try {
			name = (String) saveobj_in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			date = (String) saveobj_in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			album_list = (Vector<Album>) saveobj_in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			saveobj_in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new MainMenuFrame(name, date, album_list);
		return true;
	}
	
	
	
}
