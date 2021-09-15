package com.example.CUSHProject.controller;

import com.example.CUSHProject.Pagination.Paging;
import com.example.CUSHProject.dto.BoardCategoryDto;
import com.example.CUSHProject.dto.BoardDto;
import com.example.CUSHProject.entity.BoardCategoryEntity;
import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.repository.BoardCategoryRepository;
import com.example.CUSHProject.service.BoardService;
import com.example.CUSHProject.service.CategoryService;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;


    @GetMapping("/board/list")
    public String boardList(Model model,
                            @RequestParam(value ="page", defaultValue = "1") Integer curPageNum,
                            @RequestParam(defaultValue = "title") String searchType,
                            @RequestParam(required = false, defaultValue = "")String keyword) {
        int notice =0;
        Paging paging = new Paging();
        Page<BoardEntity> boardList = boardService.boardList(searchType, keyword, curPageNum, notice);
        double listCnt = boardService.getBoardListCnt(keyword,notice);
        paging.pageInfo(curPageNum, listCnt);
        model.addAttribute("boardList",boardList);
        model.addAttribute("paging",paging);
        return "board/boardlist";
    }

    //글쓰기
    @GetMapping("/board/write")
    public String boardWrite(Model model) {
        BoardDto boardForm = new BoardDto();
        List<BoardCategoryDto> categoryList = categoryService.getCategory();
        model.addAttribute("boardForm",boardForm);
        model.addAttribute("categoryList",categoryList);
        return "board/boardform";
    }

    @PostMapping("/board/write")
    public String boardWrite(BoardDto boardDto, Authentication authentication){
        boardService.boardWrite(boardDto, authentication.getName());
        return "redirect:/board/list";
    }

    @GetMapping("/board/content")
    public String boardContent(Model model, @RequestParam(required = false) Long id){
        boardService.boardHitUpdate(id);

        BoardDto boardForm = boardService.boardContent(id);
        String categoryName = categoryService.findCategoryById(boardForm.getCategoryId());

        model.addAttribute("boardForm",boardForm);
        model.addAttribute("categoryName",categoryName);
        return "board/boardcontent";
    }

    @GetMapping("/board/modify")
    public String boardModify(Model model, @RequestParam(required = false) Long id){
        BoardDto boardForm = boardService.boardContent(id);

        List<BoardCategoryDto> categoryList = categoryService.getCategory();
        model.addAttribute("boardForm",boardForm);
        model.addAttribute("categoryList",categoryList);

        System.out.println("______________________________");
        System.out.println("노티스: "+ boardForm.getNotice());
        System.out.println("______________________________");
        return "board/boardmodify";
    }

    @PostMapping("/board/modify")
    public String boardModify(BoardDto boardDto, Authentication authentication){
        boardService.boardModifySave(boardDto, authentication.getName());
        return "redirect:/board/list";
    }

    @ResponseBody
    @PostMapping("/uploadSummernoteImageFile")
    public JsonObject uploadSummernoteImageFile(@RequestParam("file")MultipartFile multipartFile) {
        return boardService.boardImageUpload(multipartFile);
    }

    @ResponseBody
    @DeleteMapping("/board/delete")
    public Long boardDelete(@RequestParam(required = false)Long id) {
        System.out.println("받아온 id값 : " + id);
        boardService.boardDelete(id);
        return id;
    }

    /*공지사항*/
    @GetMapping("/board/noticeboard")
    public String noticeList(Model model,
                            @RequestParam(value ="page", defaultValue = "1") Integer curPageNum,
                            @RequestParam(defaultValue = "title") String searchType,
                            @RequestParam(required = false, defaultValue = "")String keyword) {
        int notice =1;
        Paging paging = new Paging();
        Page<BoardEntity> noticeList = boardService.boardList(searchType, keyword, curPageNum,notice);
        double listCnt = boardService.getBoardListCnt(keyword,notice);
        paging.pageInfo(curPageNum, listCnt);
        model.addAttribute("noticeList",noticeList);
        model.addAttribute("paging",paging);
        return "board/noticeboard";
    }


}
