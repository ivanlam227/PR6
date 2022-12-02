package com.example.Rabota.Controller;

import com.example.Rabota.Models.Driver;
import com.example.Rabota.Models.Vy;
import com.example.Rabota.repo.DriverRepository;
import com.example.Rabota.repo.VyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class PravaController {
    @Autowired
    private VyRepository vyRepository;
    @Autowired
    private DriverRepository driverRepository;

    @GetMapping("/driver")
    public String MainGet(Model model){
        Iterable<Vy> vys = vyRepository.findAll();
        model.addAttribute("vys", vys);
        return "Add-Vy";
    }

    @GetMapping("/driver/add")
    public String driverGetAdd(@ModelAttribute("vy") Vy vy, Model model)
    {
        Iterable<Vy> vys = vyRepository.findAll();
        ArrayList<Vy> vyArrayList = new ArrayList<>();
        for (Vy vyys: vys) {
            if(vyys.getOwner()==null) {
                vyArrayList.add(vyys);
            }
        }
        model.addAttribute("vys",vyArrayList);
        return "redirect:/driver";
    }
    @PostMapping("/driver/add")
    public String blogPostAdd(@ModelAttribute("vys") @Valid Driver driver, BindingResult bindingResult, @RequestParam String name, Model model)
    {
        driver.setVy(vyRepository.findByNumber(name));
        if(bindingResult.hasErrors()){return "Add-Vy";}
        driverRepository.save(driver);
        return "redirect:/driver";
    }
    @PostMapping("/newVy")
    public String vyPostAdd(@ModelAttribute("vy") @Valid Vy vv, BindingResult bindingResult,
                              Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "Add-Vy";
        }
        vyRepository.save(vv);
        return "redirect:/driver";
    }
@GetMapping("/Add-Vy")
    public String AddVy(Model model)
    {
        Iterable<Vy> vv = vyRepository.findAll();
        model.addAttribute("vv", vv);
        return "Add-Vy";
    }
@GetMapping("/newVy")
    public String newVy(Vy vy,Model model)
    {
        return "Vy";
    }


}
