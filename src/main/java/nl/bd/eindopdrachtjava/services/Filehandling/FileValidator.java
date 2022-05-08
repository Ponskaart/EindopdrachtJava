//package nl.bd.eindopdrachtjava.services.Filehandling;
//
//import org.springframework.http.MediaType;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//
///**
// * Validator class that checks if file is a PNG or not.
// */
//public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {
//
//    @Override
//    public void initialize(ValidFile file) {
//
//    }
//
//    /**
//     * Checks if file is of a valid type.
//     */
//    @Override
//    public boolean isValid(MultipartFile multipartImage, ConstraintValidatorContext context) {
//        String contentType = multipartImage.getContentType();
//
//        boolean result = true;
//        if (!isSupportedContentType(contentType)) {
//            result = false;
//        }
//        return result;
//    }
//
//    private boolean isSupportedContentType(String contentType) {
//        return contentType.equals(MediaType.IMAGE_PNG_VALUE)
//                || contentType.equals(MediaType.IMAGE_JPEG_VALUE)
//                || contentType.equals(MediaType.IMAGE_GIF_VALUE);
//    }
//}