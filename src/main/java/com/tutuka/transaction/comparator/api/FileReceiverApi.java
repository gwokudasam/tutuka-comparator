package com.tutuka.transaction.comparator.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

/**
 * Class <code>FileReceiverApi</code> is the service interface of the FileReceiver Endpoint API
 * definition.
 *
 * @see FileReceiverApiImpl
 * @author samuel gwokuda
 * @version v1.0
 * @since v1.0
 */

@RestController
@RequestMapping("/files")
public interface FileReceiverApi {

    /**
     *
     * @param file1 The fist file to compare
     * @param file2 The second file to compare
     * @param redirectAttributes
     * @return
     * @throws IOException
     * Save files to disk and then call the service compare() method
     * Add the resultDto objects to the modelAndView which is passed back to Thymeleaf.
     */

    @PostMapping
    ModelAndView postFiles(@RequestParam("file1") final MultipartFile file1,
                           @RequestParam("file2") final MultipartFile file2,
                           final RedirectAttributes redirectAttributes) throws IOException;
}
