import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Class to zip/unzip files or directories
 * 
 * 		Usage:
 * 		zipping a folder
 * 		IZipper iZipper = new Zipper();
 * 		iZipper.zipIt("C:\\Users\\someone\\test.zip", "C:\\Users\\someone\\folderToZip");
 * 
 * 		unzipping a folder: 
 * 		iZipper.unZip("C:\\Users\\someone\\test.zip", "C:\\Users\\someone\\folderToExtractTo");
 * 
 * @author JARE
 */
public class Zipper {

	private List<String> fileList;

	Zipper() {
		fileList = new ArrayList<String>();
	}

/**
* Unzip
*/
	public void unZip(final String zipFile,final String outputFolder) {

		TELog.info("unzipping zipFile: " + zipFile + " to outputFolder: " + outputFolder);
		byte[] buffer = new byte[1024];

		try {

			// create output directory if it does not exist
			File folder = new File(outputFolder);

			if (!folder.exists()) {
				folder.mkdir();
			}

			// get the zip file content
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {

				String fileName = ze.getName();
				File newFile = new File(outputFolder + File.separator + fileName);

				// create all non existent folders
				// else you will hit FileNotFoundException for compressed folder
				new File(newFile.getParent()).mkdirs();

				FileOutputStream fos = new FileOutputStream(newFile);

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		System.out.println("unzipping....done");
	}
	
	/*
	 * Zip
	 */
	public void zipIt(final String zipFile,final String folderToZip) {
		generateFileList(new File(folderToZip), folderToZip);
		byte[] buffer = new byte[1024];

		try {

			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			System.out.println("Output to Zip : " + zipFile);

			for (String file : this.fileList) {

				System.out.println("File Added : " + file);
				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream(folderToZip + File.separator + file);

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}

			zos.closeEntry();
			// remember to close it
			zos.close();

			System.out.println("Done");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * recursive method to get a list of filenames to zip
	 * 
	 * @param node
	 * @param folderToZip
	 */
	 private void generateFileList(File node, String folderToZip) {

		// add file only
		if (node.isFile()) {
			fileList.add(generateZipEntry(node.getAbsoluteFile().toString(), folderToZip));
		}

		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename : subNote) {
				generateFileList(new File(node, filename), folderToZip);
			}
		}

	}

	/**
	 * Format the file path for zip
	 * 
	 * @param file
	 * @param folderToZip
	 * @return String .. formated FilePath
	 */
	private String generateZipEntry(String file, String folderToZip) {
		return file.substring(folderToZip.length() + 1, file.length());
	}

}
