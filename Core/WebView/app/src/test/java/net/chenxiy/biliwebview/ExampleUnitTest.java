package net.chenxiy.biliwebview;

import net.chenxiy.biliapi.ApiEndpointInterface;
import net.chenxiy.biliapi.RetrofitInstance;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit activity_main, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    public static final String COOKIE="sid=8b8x8hix; buvid3=2B21B26A-4F8D-4F88-9012-C23FEBB4D24984615infoc; DedeUserID=402053210; DedeUserID__ckMd5=f2a48e4e47b02818; SESSDATA=b29154fa%2C1553240075%2C9411a221; bili_jct=48bc047e993dafc8b1a9c938ee056153";
    public static final String CSRF_TOKEN="48bc047e993dafc8b1a9c938ee056153";
    public static final Integer USER_UID=402053210;
    public static final Integer PRI_FID=3266680;
    public static final Integer PUB_FID=3266765;
    public static final Integer Default_FID=3252985;
    public static final Integer TEST_AVID=42463258;
    public static final Integer TEST_CID=74506946;

    public static void main(String args[]){
        ApiEndpointInterface apiService =
                RetrofitInstance.getInstance().create(ApiEndpointInterface.class);
//        Call<UserResponse> call = apiService.getUser("3379951");
//
//
//        call.enqueue(new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                UserResponse userResponse=response.body();
//                UserData userData=userResponse.getUserData();
//                System.out.println(userData.getSign());
//            }
//
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                    System.out.println(t.getCause());
//            }
//        });

//        Call<AddDelFolderResponse> call=apiService.newFavFolder(COOKIE,"aha123",FAV_FOLDER_PUBLIC,CSRF_TOKEN,JSONP);
//        call.enqueue(new Callback<AddDelFolderResponse>() {
//            @Override
//            public void onResponse(Call<AddDelFolderResponse> call, Response<AddDelFolderResponse> response) {
//                System.out.println(response.body().getAvData().getFid());
//            }
//
//            @Override
//            public void onFailure(Call<AddDelFolderResponse> call, Throwable t) {
//
//            }
//        });

//         Call<FavFolderInfoResponse> call=apiService.getFavFolderInfo(COOKIE,USER_UID);
//         call.enqueue(new Callback<FavFolderInfoResponse>() {
//             @Override
//             public void onResponse(Call<FavFolderInfoResponse> call, Response<FavFolderInfoResponse> response) {
//                 List<FolderArchive> folderArchives=response.body().getAvData().getFolderArchive();
//                 for(FolderArchive folderArchive : folderArchives){
//                     System.out.println(folderArchive.getFid());
//                     System.out.println(folderArchive.getName());
//                 }
//             }
//
//             @Override
//             public void onFailure(Call<FavFolderInfoResponse> call, Throwable t) {
//
//             }
//         });

//        Call<FavFolderContentInfoResponse>call=apiService.getFavFolderContentInfo(COOKIE,USER_UID,PRI_FID,0,1,ORDER_FAV_TIMES);
//
//        call.enqueue(new Callback<FavFolderContentInfoResponse>() {
//            @Override
//            public void onResponse(Call<FavFolderContentInfoResponse> call, Response<FavFolderContentInfoResponse> response) {
//                List<FavSong> favSongs=response.body().getAvData().getFavSongs();
//                for(FavSong favSong:favSongs){
//                    System.out.println(favSong.getTitle());
//                }
//                return;
//            }
//
//            @Override
//            public void onFailure(Call<FavFolderContentInfoResponse> call, Throwable t) {
//
//            }
//        });

//     Call<AvInfoResponse> call=apiService.getAvInfo(TEST_AVID);
//     call.enqueue(new Callback<AvInfoResponse>() {
//         @Override
//         public void onResponse(Call<AvInfoResponse> call, Response<AvInfoResponse> response) {
//             System.out.println(response.body().getAvData().getOwner().getFace());
//         }
//
//         @Override
//         public void onFailure(Call<AvInfoResponse> call, Throwable t) {
//
//         }
//     });

//        Call<DownloadInfoResponse> call=apiService.getDownloadInfo(TEST_AVID,TEST_CID);
//        call.enqueue(new Callback<DownloadInfoResponse>() {
//            @Override
//            public void onResponse(Call<DownloadInfoResponse> call, Response<DownloadInfoResponse> response) {
//                String baseUrl=response.body().getData().getDownloadResources().getVideo().get(0).getBaseUrl();
//                System.out.println(baseUrl);
//            }
//
//            @Override
//            public void onFailure(Call<DownloadInfoResponse> call, Throwable t) {
//
//            }
//        });

//        Call<AddDelFolderResponse> call=apiService.deleteFavFolder(COOKIE,3269757,CSRF_TOKEN, Constants.JSONP);
//        call.enqueue(new Callback<AddDelFolderResponse>() {
//            @Override
//            public void onResponse(Call<AddDelFolderResponse> call, Response<AddDelFolderResponse> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<AddDelFolderResponse> call, Throwable t) {
//
//            }
//        });

//        Call<CodeResponse> call=apiService.addSongToFavFolder(COOKIE,TEST_AVID,Default_FID,CSRF_TOKEN,Constants.JSONP);
//        call.enqueue(new Callback<CodeResponse>() {
//            @Override
//            public void onResponse(Call<CodeResponse> call, Response<CodeResponse> response) {
//                System.out.println(response.body().getMessage());
//            }
//
//            @Override
//            public void onFailure(Call<CodeResponse> call, Throwable t) {
//
//            }
//        });



    }
}

