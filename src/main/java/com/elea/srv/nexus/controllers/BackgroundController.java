package com.elea.srv.nexus.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.hibernate.type.ImageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.elea.srv.nexus.dao.BackgroundRepository;
import com.elea.srv.nexus.models.Background;

@RestController
@CrossOrigin
@RequestMapping("backgrounds")
public class BackgroundController {

	@Autowired
	BackgroundRepository bgDao;

	// GET Background
	@GetMapping("")
	public List<Background> getBackgrounds() {
		return bgDao.findAll();
	}

	// UPDATE Background
	@PutMapping("")
	@ResponseBody
	public String updateBg(@RequestBody Background bg) {
		Background b = null;
		b = bgDao.findOne(bg.getId());

		if (b != null) {
			try {
				bgDao.save(bg);
				return "updted!";
			} catch (Exception e) {
				e.printStackTrace();
				return "Fail on update..";
			}
		} else {
			return "Fail not exist..";
		}

	}

	// ADD Background
	@PostMapping("")
	@ResponseBody
	public String addBg( @RequestParam("file") MultipartFile file, @RequestParam("nom") String name) {
		
		if (!file.isEmpty()) {
            try {
                ImageType image = (ImageType) file;
                Background nbg = new Background();
                
                nbg.setImage(image);
                nbg.setNom(name);
                
                bgDao.save(nbg);
                return "You successfully uploaded " + name + " into " + name + "-uploaded !";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
//		try {
//			bgDao.save(file);
//			return "Added!";
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "Fail on add!";
//		}
	}

	// DEL background
	@DeleteMapping("")
	@ResponseBody
	public String delBg(@RequestBody Background bg) {
		try {
			bgDao.delete(bg);
			return "Delete!";
		} catch (Exception e) {
			return "Fail on Delete";
		}
	}

}
