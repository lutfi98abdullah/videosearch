package vttp2022.ssf.assessment.videosearch.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.ssf.assessment.videosearch.models.Game;
import vttp2022.ssf.assessment.videosearch.service.SearchService;

@Controller
@RequestMapping(path="/search")
public class SearchController {


    @Autowired
    private SearchService searchSvc;


        @GetMapping(path = "/search")
        public String gameList (
            @RequestParam(name="name", required = true)String name, 
            @RequestParam(name = "number", defaultValue = "10") Integer number, Model model) {

                


                List<Game> theList= searchSvc.search(name, number);
                               

                

                model.addAttribute("gamesList", theList);
                return "new";
            }
        
}
