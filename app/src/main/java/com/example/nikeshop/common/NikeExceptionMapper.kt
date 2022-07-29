

import com.example.nikeshop.R
import com.example.nikeshop.common.NikeException
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import org.json.JSONObject
import timber.log.Timber

class NikeExceptionMapper {
    companion object {
        fun map(throwable: Throwable): NikeException {
            if (throwable is HttpException) {
                try {
                    val errorJsonObject = JSONObject(throwable.response()?.errorBody()!!.string())
                    val errorMessage=errorJsonObject.getString("message")
                    return NikeException(if (throwable.code()==401) NikeException.Type.AUTH else NikeException.Type.SIMPLE,serverMessage = errorMessage)
                } catch (exception: Exception) {
                    Timber.e(exception)
                }
            }

            return NikeException(NikeException.Type.SIMPLE, R.string.unknownError)
        }
    }
}