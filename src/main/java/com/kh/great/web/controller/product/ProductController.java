package com.kh.great.web.controller.product;

import com.kh.great.domain.common.file.AttachCode;
import com.kh.great.domain.dao.uploadFile.UploadFile;
import com.kh.great.domain.svc.uploadFile.UploadFileSVC;
import com.kh.great.domain.dao.product.Product;
import com.kh.great.domain.svc.product.ProductSVC;
import com.kh.great.web.dto.product.SaveForm;
import com.kh.great.web.dto.product.UpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductSVC productSVC;
    private final UploadFileSVC uploadFileSVC;

    //등록 양식
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("form", new SaveForm());
        return "product/addForm";
    }

    //등록처리
    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("form") SaveForm saveForm, BindingResult bindingResult, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        //기본검증
        if(bindingResult.hasErrors()){
            log.info("bindingResult={}", bindingResult);
            return "product/addForm";
        }
        // 필드 검증
        // 글제목 30자 초과 금지
        if (saveForm.getPTitle().length() > 30) {
            bindingResult.rejectValue("ptitle", "product.ptitle", new Integer[]{30}, "글제목 30자 초과");
            log.info("bindingResult={}", bindingResult);
            return "product/addForm";
        }
        // 상품명 10자 이내
        if (saveForm.getPName().length() > 10) {
            bindingResult.rejectValue("pName", "product.pname", new Integer[]{10}, "상품명 10자 초과");
            log.info("bindingResult={}", bindingResult);
            return "product/addForm";
        }
        // 총수량 99999초과 금지
        if (saveForm.getTotalCount() > 99999) {
            bindingResult.rejectValue("totalCount", "product.totalcount", new Integer[]{99999}, "판매수량 초과");
            log.info("bindingResult={}", bindingResult);
            return "product/addForm";
        }
        // 정상가 > 할인가
        if (saveForm.getNormalPrice() < saveForm.getSalePrice()) {
            bindingResult.reject("product.pricerange", "할인가보다 정상가가 커야합니다.");
            log.info("bindingResult={}", bindingResult);
            return "product/addForm";
        }

        Product product = new Product();
        BeanUtils.copyProperties(saveForm, product);
        Long pNum = 0l;

        HttpSession session = request.getSession(false);
        Object memNumber = session.getAttribute("memNumber");
        product.setOwnerNumber((Long)memNumber);

        //상품 메타정보 저장
        if (saveForm.getFiles().get(0).isEmpty()) {
            pNum = productSVC.save(product);
        } else if (!saveForm.getFiles().get(0).isEmpty()) {
            pNum = productSVC.save(product, saveForm.getFiles());
        }

        redirectAttributes.addAttribute("num", pNum);
        return "redirect:/product/{num}";
    }

    //수정 화면
    @GetMapping("/{num}/edit")
    public String editFrom(@PathVariable("num") Long num, Model model) {
        //1) 상품조회
        Product findedProduct = productSVC.findByProductNum(num);
        UpdateForm updateForm = new UpdateForm();
        BeanUtils.copyProperties(findedProduct, updateForm);

        //2) 상품 이미지 조회
        List<UploadFile> uploadFiles = uploadFileSVC.getFilesByCodeWithRid(AttachCode.P0102.name(), num);
        if(uploadFiles.size() > 0 ){
            List<UploadFile> imageFiles = new ArrayList<>();
            for (UploadFile file : uploadFiles) {
                imageFiles.add(file);
            }
            updateForm.setImageFiles(imageFiles);
        }
        System.out.println("num = " + num);
        model.addAttribute("form", updateForm);

        return "product/updateForm";
    }

    //수정 처리
    @PostMapping("/{num}/edit")
    public String edit(@PathVariable("num") Long num, @Valid @ModelAttribute("form") UpdateForm updateForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Product product = new Product();
        BeanUtils.copyProperties(updateForm, product);

        System.out.println("updateForm.toString() = " + updateForm.toString());
        System.out.println("updateForm.getFiles().get(0) = " + updateForm.getFiles().get(0));

        //상품 메타정보 수정
        if (updateForm.getFiles().get(0).isEmpty()) {
            productSVC.update(num, product);
        } else if (!updateForm.getFiles().get(0).isEmpty()) {
            productSVC.update(num, product, updateForm.getFiles());
        }

//        int updatedRow = productSVC.update(num, product);
//        if (updatedRow == 0) {
//            return  "redirect:/products/{num}";
//        }
        System.out.println("in num = " + num);
//        redirectAttributes.addAttribute("num", num);
//        return "redirect:/product/{num}";
        return "redirect:/product/" + num;
    }

    //삭제처리
    @GetMapping("/{num}/del")
    public String delete(@PathVariable("num") Long num) {
        int deletedRow = productSVC.deleteByProductNum(num);
        if (deletedRow == 0) {
            return "redirect:/product/" + num;
        }
        return "redirect:/";
    }

    //상품관리
//    @GetMapping("/{ownerNumber}/manage")
//    public String manage(@PathVariable("ownerNumber") Long ownerNumber, Model model) {
//        List<Product> list = productSVC.pManage(ownerNumber);
//        model.addAttribute("list", list);
//
//        return "product/manage";
//    }
    @GetMapping("/{ownerNumber}/manage")
    public String manage(@PathVariable("ownerNumber") Long ownerNumber, Model model) {

        List<Product> list = productSVC.manage(ownerNumber);
        model.addAttribute("list", list);

        return "product/manage";
    }

    //판매 내역 목록
    @GetMapping("/{ownerNumber}/saleList")
    public String saleList(@PathVariable("ownerNumber") Long ownerNumber, Model model) {
        List<Product> list = productSVC.saleList(ownerNumber);
        model.addAttribute("list", list);

        return "product/saleList";
    }
}