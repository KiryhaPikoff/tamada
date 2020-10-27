package ul.ulstu.tamada.rest;


import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Log
@RestController
public class HelloController {

    @GetMapping("/hello")
    public ResponseEntity<String> getHello()
    {
        return new ResponseEntity<>("Hello from tamada da backend!", HttpStatus.OK);
    }

    @GetMapping("/lab3")
    public ResponseEntity<List<IterationDto>> getThird(
           @RequestParam("e") BigDecimal e,
           @RequestParam("x0") BigDecimal x0,
           @RequestParam("h") BigDecimal h,
           @RequestParam("addScale") Integer addScale
    ) {
        var iterations = new LinkedList<IterationDto>();
        var iteration_num = 1;
        BigDecimal xn_1 = null;
        var x = x0;
        BigDecimal diff_m = null;
        iterations.add(IterationDto.builder().result(x0).difference("Не вычисляется").iteration(0).build());
        while (diff_m == null || diff_m.compareTo(e) != -1) {
            var iteration = new IterationDto();

            var fx = BigDecimal.valueOf(0.5 * Math.exp(x.negate().multiply(x).doubleValue())).add(BigDecimal.valueOf(x.multiply(BigDecimal.valueOf(Math.cos(x.doubleValue()))).doubleValue()));
            var x_h = x.add(h);
            var fx_h = BigDecimal.valueOf(0.5 * Math.exp(x_h.negate().multiply(x_h).doubleValue())).add(BigDecimal.valueOf(x_h.multiply(BigDecimal.valueOf(Math.cos(x_h.doubleValue()))).doubleValue()));


            xn_1 = x;
            x = x.subtract(fx.multiply(h).divide(fx_h.subtract(fx), RoundingMode.HALF_UP));

            var diff = x.subtract(xn_1).abs().setScale(e.scale() + addScale, RoundingMode.CEILING);
            diff_m = diff;

            iteration.setIteration(iteration_num);
            iteration_num++;
            iteration.setDifference(diff.setScale(e.scale() + addScale, RoundingMode.CEILING).toString());
            iteration.setResult(x.setScale(e.scale(), RoundingMode.CEILING));

            iterations.add(iteration);
        }
        return new ResponseEntity<>(iterations, HttpStatus.OK);
    }
}
