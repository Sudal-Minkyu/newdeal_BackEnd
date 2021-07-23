package com.broadwave.backend.performance.price;

import com.broadwave.backend.common.AjaxResponse;
import com.broadwave.backend.common.ResponseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Minkyu
 * Date : 2021-07-07
 * Remark : 뉴딜 관리자 전용 성능개선사업평가 년도별 환율 PriceRestController
 */
@Slf4j
@RestController
@RequestMapping("/api/price")
public class PriceRestController {

    private final PriceService priceService;

    @Autowired
    public PriceRestController(PriceService priceService){
        this.priceService = priceService;
    }

    @PostMapping("save")
    public ResponseEntity<Map<String,Object>> priceSave(@RequestParam("piYear")Double piYear,
                                                                                    @RequestParam("piPrice")Double piPrice,
                                                                                    HttpServletRequest request){

        AjaxResponse res = new AjaxResponse();
        Price price = new Price();

        String JWT_AccessToken = request.getHeader("JWT_AccessToken");
        String insert_id = request.getHeader("insert_id");
        log.info("JWT_AccessToken : "+JWT_AccessToken);
        log.info("insert_id : "+insert_id);

        PriceDto priceDto = priceService.findByPiYearCustom(piYear);

        if(priceDto==null){
            // 신규생성
            price.setPiYear(piYear);
            price.setPiPrice(piPrice);
            price.setInsert_id(insert_id);
            price.setInsertDateTime(LocalDateTime.now());
        }else{
            // 수정
            price.setId(priceDto.getId());
            price.setPiYear(piYear);
            price.setPiPrice(piPrice);
        }

        priceService.save(price);
        return ResponseEntity.ok(res.success());
    }

    @GetMapping("list")
    public ResponseEntity<Map<String,Object>> priceList(@RequestParam("piYearSearch")Double piYearSearch,
                                                                                    @RequestParam("piPriceSearch")Double piPriceSearch,
                                                                                    Pageable pageable){
        AjaxResponse res = new AjaxResponse();
        Page<PriceDto> price = priceService.findByYearPrice(piYearSearch, piPriceSearch, pageable);
        return ResponseEntity.ok(res.ResponseEntityPage(price));
    }

    @PostMapping("del")
    public  ResponseEntity<Map<String,Object>> priceDel(@RequestParam (value="id", defaultValue="") Long id){
        AjaxResponse res = new AjaxResponse();

        Optional<Price> optionalPrice = priceService.findById(id);

        //정보가있는지 체크
        if (optionalPrice.isEmpty()){
            return ResponseEntity.ok(res.fail(ResponseErrorCode.NDE006.getCode(),ResponseErrorCode.NDE006.getDesc(),null,null));
        }else{
            Price price = optionalPrice.get();
            priceService.delete(price);
        }
        return ResponseEntity.ok(res.success());
    }

}
