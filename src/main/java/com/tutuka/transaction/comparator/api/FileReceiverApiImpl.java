package com.tutuka.transaction.comparator.api;

import com.tutuka.transaction.comparator.service.FileReceiverService;
import com.tutuka.transaction.comparator.service.model.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Paths;

import static com.tutuka.transaction.comparator.service.FileReceiverServiceImpl.DIRECTORY_MARKER;
import static com.tutuka.transaction.comparator.service.FileReceiverServiceImpl.TUTUKA_FOLDER;

/**
 * Class <code>FileReceiverApi</code> is the implementation of the main FileReceiverApi Endpoint API
 * definition.
 *
 * @author samuel gwokuda
 * @version v1.0
 * @see FileReceiverApi
 * @since v1.0
 */

@Controller
@RequiredArgsConstructor
@Slf4j
public class FileReceiverApiImpl implements FileReceiverApi {

    private final FileReceiverService receiverService;

    /**
     * @param file1              The fist file to compare
     * @param file2              The second file to compare
     * @param redirectAttributes
     * @return
     * @throws IOException
     * Save files to disk and then call the service compare() method
     * Add the resultDto objects to the modelAndView which is passed back to Thymeleaf.
     */
    @Override
    public ModelAndView postFiles(@RequestParam("file1") final MultipartFile file1,
                                  @RequestParam("file2") final MultipartFile file2,
                                  final RedirectAttributes redirectAttributes) throws IOException {

        final ModelAndView modelAndView = new ModelAndView();

        file1.transferTo(Paths.get(TUTUKA_FOLDER + DIRECTORY_MARKER + file1.getOriginalFilename()));
        file2.transferTo(Paths.get(TUTUKA_FOLDER + DIRECTORY_MARKER + file2.getOriginalFilename()));

        final Pair<ResultDto, ResultDto> resultPair = receiverService.compareFiles(file1.getOriginalFilename(), file2.getOriginalFilename());

        modelAndView.addObject("file1", resultPair.getFirst());
        modelAndView.addObject("file2", resultPair.getSecond());

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file1.getOriginalFilename() + " and " + file2.getOriginalFilename() + "!");

        modelAndView.setViewName("index");
        modelAndView.addObject("message", "You successfully uploaded " + file1.getOriginalFilename() + " and " + file2.getOriginalFilename() + "!");
        return modelAndView;
    }
}
