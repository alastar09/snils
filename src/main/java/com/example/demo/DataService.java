package com.example.demo;

import com.example.demo.snils;
import com.example.demo.snilsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {

    private final snilsRepository snilsRepository;

    public DataService(snilsRepository snilsRepository) {
        this.snilsRepository = snilsRepository;
    }

    public List<snils> findAllsnils(String filterText){
        if(filterText==null || filterText.isEmpty()){
            return snilsRepository.findAll();
        } else{
            return snilsRepository.search(filterText);
        }
    }

    public long countSnils(){
        return  snilsRepository.count();
    }

    public void deleteSnils(snils snils){
        snilsRepository.delete(snils);
    }

    public void saveSnils(snils snils){
        if(snils==null){
            System.err.println("Snils is null.");
        }

        snilsRepository.save(snils);
    }
}
