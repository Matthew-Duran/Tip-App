package com.MatthewDuran.Tip_App;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TipController {

    private static final double WAITER_WEIGHT = 100.0;
    private static final double FOODRUNNER_WEIGHT = 75.0;
    private static final double BUSBOY_WEIGHT = 65.0;

    @PostMapping("/calculate")
    public String calculateTips(
            @RequestParam("waiters") int numWaiters,
            @RequestParam("foodRunners") int numFoodRunners,
            @RequestParam("busboys") int numBusBoys,
            @RequestParam("tipPool") double totalTips,
            Model model) {

        double totalShares = numWaiters*WAITER_WEIGHT + numFoodRunners*FOODRUNNER_WEIGHT + numBusBoys*BUSBOY_WEIGHT;

        double waiterTotal = (numWaiters>0) ? (WAITER_WEIGHT / totalShares) * totalTips : 0;
        double foodRunnerTotal = (numFoodRunners>0) ? (FOODRUNNER_WEIGHT / totalShares) * totalTips : 0;
        double busboyTotal = (numBusBoys>0) ? (BUSBOY_WEIGHT / totalShares) * totalTips : 0;

        model.addAttribute("waiters", numWaiters);
        model.addAttribute("foodRunners", numFoodRunners);
        model.addAttribute("busboys", numBusBoys);
        model.addAttribute("tipPool", totalTips);

        model.addAttribute("waiterShare", (numWaiters>0) ? waiterTotal / numWaiters : 0);
        model.addAttribute("foodRunnerShare", (numFoodRunners>0) ? foodRunnerTotal / numFoodRunners : 0);
        model.addAttribute("busboyShare", (numBusBoys>0) ? busboyTotal / numBusBoys : 0);

        return "results"; // renders results.html
    }
}
