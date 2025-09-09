package com.MatthewDuran.Tip_App;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TipController {
    //Base Percentages
    private static final double WAITER_WEIGHT = 100.0;
    private static final double FOODRUNNER_WEIGHT = 75.0;
    private static final double BUSBOY_WEIGHT = 65.0;

    @PostMapping("/calculate")
    public String calculateTips(
            //Framework for base values
            @RequestParam("waiters") int numWaiters,
            @RequestParam("foodRunners") int numFoodRunners,
            @RequestParam("busboys") int numBusBoys,
            @RequestParam("tipPool") double totalTips,
            Model model) {

        double totalShares = numWaiters*WAITER_WEIGHT + numFoodRunners*FOODRUNNER_WEIGHT + numBusBoys*BUSBOY_WEIGHT;

        if (totalShares == 0) {
            model.addAttribute("error", "No staff entered");
            model.addAttribute("tipPool", totalTips);
            return "results";
        }


        double waiterTotal = (numWaiters > 0) ? (WAITER_WEIGHT / totalShares) * totalTips : 0;
        double foodRunnerTotal = (numFoodRunners > 0) ? (FOODRUNNER_WEIGHT / totalShares) * totalTips : 0;
        double busboyTotal = (numBusBoys > 0) ? (BUSBOY_WEIGHT / totalShares) * totalTips : 0;

        double waiterShare = rounding((numWaiters > 0) ? waiterTotal : 0);
        double foodRunnerShare = rounding((numFoodRunners > 0) ? foodRunnerTotal : 0);
        double busboyShare = rounding((numBusBoys > 0) ? busboyTotal : 0);

        double waiterTotalPayout = rounding(waiterShare * numWaiters);
        double foodRunnerTotalPayout = rounding(foodRunnerShare * numFoodRunners);
        double busboyTotalPayout = rounding(busboyShare * numBusBoys);


        model.addAttribute("waiters", numWaiters);
        model.addAttribute("foodRunners", numFoodRunners);
        model.addAttribute("busboys", numBusBoys);
        model.addAttribute("tipPool", totalTips);

        model.addAttribute("waiterShare", waiterShare);
        model.addAttribute("foodRunnerShare", foodRunnerShare);
        model.addAttribute("busboyShare", busboyShare);

        model.addAttribute("waiterTotal", waiterTotalPayout);
        model.addAttribute("foodRunnerTotal", foodRunnerTotalPayout);
        model.addAttribute("busboyTotal", busboyTotalPayout);


        return "results";
    }

    private double rounding(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

}
