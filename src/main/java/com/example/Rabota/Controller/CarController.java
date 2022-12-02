package com.example.Rabota.Controller;

import com.example.Rabota.Models.Car;
import com.example.Rabota.Models.Complect;
import com.example.Rabota.Models.Employee;
import com.example.Rabota.repo.CarRepository;
import com.example.Rabota.repo.ComplectRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CarController {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ComplectRepository complectRepository;
    @GetMapping("/Car")
    public String GetRab(Model model)
    {
        Iterable<Complect> complects = complectRepository.findAll();
        Iterable<Car> car = carRepository.findAll();
        model.addAttribute("car",car);
        model.addAttribute("complect", complects);
        return "MainModel";
    }
    @GetMapping("/Add/Car")
    public String GetAddCar(Car car, Model model)
    {
        Iterable<Complect> complects=complectRepository.findAll();
        ArrayList<Complect> complectArrayList=new ArrayList<>();
        for (Complect complect: complects)
        {
            if (complect.getCompl()==null)
            {
                complectArrayList.add(complect);
            }
        }
        model.addAttribute("complect", complects);
        return "Add-Car";
    }

    @PostMapping("/Add/Car")
    public String blogPostAdd(@ModelAttribute("car")@Valid Car car, BindingResult bindingResult, @RequestParam String compl,  Model model)
    {

        car.setComplect(complectRepository.findByCompl(compl));
        if(bindingResult.hasErrors())
        {
            return "Add-Car";
        }
        carRepository.save(car);
        return "redirect:/Car";
    }




    @GetMapping( path = "/Search/Car")
    public String blogFilter(Model model)
    {
        return "Search-Car";
    }

    @GetMapping("/Search/Car-result")
    public String blogResult(@RequestParam String marks, Model model)
    {
        List<Car> cars = carRepository.findByMarks(marks);
        model.addAttribute("result", cars);
        return "Search-Car";
    }
    @PostMapping("/Search/Car")
    public String simpleSearch(@RequestParam String marks, Model model)
    {
        List<Car> cars = carRepository.findByMarksContains(marks);
        model.addAttribute("result", cars);
        return "Search-Car";
    }
    @GetMapping("/blog/Car/{id}/Edit")
    public String CarEdit(@PathVariable(value = "id") Long id, Model model)


            {
                Car car = carRepository.findById(id).orElseThrow(()
                        ->new IllegalArgumentException("Invalid car Id" + id));
                model.addAttribute("car",car);
                return "Edit-Car";
            }

    @PostMapping("/blog/Car/{id}/Edit")
    public String CarUpdate(
            @ModelAttribute("car")@Valid  Car car, BindingResult bindingResult,
            @PathVariable("id") long id) {

        car.setId(id);
        if (bindingResult.hasErrors()) {
            return "Edit-Car";
        }
        carRepository.save(car);
        return "redirect:/Car";
    }
    @PostMapping("/blog/Car/{id}/Remove")
    public String blogCarDelete(
            @PathVariable(value = "id") Long id,
            Model model) {
        Car car = carRepository.findById(id).orElseThrow();
        carRepository.delete(car);
        return "redirect:/Car";
    }
    @GetMapping("/blog/Car/{id}")
    public String CarDetails(@PathVariable(value = "id") Long id, Model model) {
        Optional<Car> car = carRepository.findById(id);
        ArrayList<Car> lis = new ArrayList<>();
        car.ifPresent(lis::add);
        model.addAttribute("car", lis);
            return "blog-CarDetails";

    }
}
