package com.rsurpport.notice.controller;

import com.rsurpport.common.vo.ErrorCode;
import com.rsurpport.common.vo.Result;
import com.rsurpport.notice.service.NoticeService;
import com.rsurpport.notice.vo.NoticeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;



/**
 * <pre>
 * 공지사항을 위한 컨트롤러
 * </pre>
 *
 * @author 오수정
 * @since 2022. 01. 21.
 * @see
 */


@RestController
@RequestMapping("/api/v1/board")
@Cacheable
public class NoticeController {



        @Autowired
        private NoticeService noticeService;


        /*   공지사항 목록 조회 */
        @GetMapping(value = "/notices")
        public Result listNotice(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(required = false, defaultValue = "") String searchText) {
            Result ret = new Result();
            ret.setCode(ErrorCode.SUCCESS);
            ret.setMessage(ErrorCode.SUCCESS.getMessage());
            ret.setResult(noticeService.listNotice(page, size, searchText));
            return ret;
        }




         /*   공지사항 상세 조회 */
        @GetMapping(value = "/notices/{id}")
        public Result getNotice(@PathVariable Long id) {
            Result ret = new Result();
            ret.setCode(ErrorCode.SUCCESS);
            ret.setMessage(ErrorCode.SUCCESS.getMessage());
            ret.setResult(noticeService.getNotice(id));
            return ret;
        }




        /*   공지사항 등록  */
        @PostMapping(value = "/notices")
        public Result insertNotice(@Valid NoticeVo noticeVo,
                                   @RequestPart(value = "attachedFileList", required = false) List<MultipartFile> attachedFileList) throws Exception {
            noticeService.insertNotice(noticeVo, attachedFileList);
            Result ret = new Result();
            ret.setCode(ErrorCode.SUCCESS);
            ret.setMessage(ErrorCode.SUCCESS.getMessage());
            return ret;
        }



          /*   공지사항 수정  */
        @PutMapping(value = "/notices")
        public Result updateNotice(@Valid NoticeVo noticeVo,
                                   @RequestParam(value = "deletedFileIdList", required = false) List<Long> deletedFileIdList,
                                   @RequestParam(value = "attachedFileList", required = false) List<MultipartFile> attachedFileList) throws Exception {
            noticeService.updateNotice(noticeVo, deletedFileIdList, attachedFileList);
            Result ret = new Result();
            ret.setCode(ErrorCode.SUCCESS);
            ret.setMessage(ErrorCode.SUCCESS.getMessage());
            return ret;
        }



         /*   공지사항 삭제  */
        @DeleteMapping(value = "/notices/{id}")
        public Result deleteNotice(@PathVariable Long id) {
            noticeService.deleteNotice(id);
            Result ret = new Result();
            ret.setCode(ErrorCode.SUCCESS);
            ret.setMessage(ErrorCode.SUCCESS.getMessage());
            return ret;
        }
}
