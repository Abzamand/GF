package id.co.qualitas.service.interfaces;

import id.co.qualitas.domain.response.WSMessage;

public interface SettingService {
	WSMessage getProfile(String username);
	WSMessage updateProfile(Object request);
	WSMessage changePassword(Object request);
}
