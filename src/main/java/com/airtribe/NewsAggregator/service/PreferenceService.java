package com.airtribe.NewsAggregator.service;

import com.airtribe.NewsAggregator.DTOs.PreferenceDTO;
import com.airtribe.NewsAggregator.entity.Preference;
import com.airtribe.NewsAggregator.entity.User;
import com.airtribe.NewsAggregator.exceptions.UserPreferenceNotFoundException;
import com.airtribe.NewsAggregator.repository.PreferenceRepository;
import com.airtribe.NewsAggregator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreferenceService {
    @Autowired
    PreferenceRepository _preferenceRepository;

    @Autowired
    UserRepository _userRepository;

    private User getUserFromUsername(String username){
        User user = _userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("User not Found");
        }
        return user;
    }

    private Preference getUserPreference(User user) throws UserPreferenceNotFoundException{
        Preference preference = _preferenceRepository.findByUser(user);
        if(preference == null){
            throw new UserPreferenceNotFoundException("No Preference found for User to Update");
        }
        return preference;
    }

    public PreferenceDTO getPreferences(String username) throws UserPreferenceNotFoundException{
        User user = getUserFromUsername(username);
        Preference preference =  _preferenceRepository.findByUser(user);
        return new PreferenceDTO(preference.getCategory(),preference.getCountry(),preference.getTopic());
    }

    public void updatePreferences(String username, PreferenceDTO preferencesDto) throws UserPreferenceNotFoundException {
        User user = getUserFromUsername(username);

        Preference preference = getUserPreference(user);
        if(preferencesDto.getCategory() != null){
            preference.setCategory(preferencesDto.getCategory());
        }
        if(preferencesDto.getCountry() != null){
            preference.setCountry(preferencesDto.getCountry());
        }
        if(preferencesDto.getTopic() != null){
            preference.setTopic(preferencesDto.getTopic());
        }
        _preferenceRepository.save(preference);
    }

    public void addPreference(String username, PreferenceDTO preferenceDTO) {
        User user = getUserFromUsername(username);

        Preference preference = new Preference();
        preference.setCountry(preferenceDTO.getCountry());
        preference.setUser(user);

        if(preferenceDTO.getCategory() != null){
            preference.setCategory(preferenceDTO.getCategory());
        }
        if(preferenceDTO.getTopic() != null){
            preference.setTopic(preferenceDTO.getTopic());
        }
        _preferenceRepository.save(preference);
    }
}
