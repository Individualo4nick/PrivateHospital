package com.example.privatehospital.Services.Impl;

import static com.example.privatehospital.Entities.QStaff.staff;

import com.example.privatehospital.DTOs.RecordDto;
import com.example.privatehospital.Entities.ClientRecord;
import com.example.privatehospital.Entities.Record;
import com.example.privatehospital.Entities.Staff;
import com.example.privatehospital.Repositories.QPredicates;
import com.example.privatehospital.Repositories.StaffRepository;
import com.example.privatehospital.Services.StaffService;
import com.example.privatehospital.StaffFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepository;
    @Value("${files.path}")
    private String imagePath;
    public void saveStaffById(Long id){
        Staff staff = new Staff();
        staff.setId(id);
        staffRepository.save(staff);
    }

    public Staff getStaffInfo(Long id) {
        return staffRepository.getStaffById(id);
    }

    @Override
    public void saveStaff(Staff staff) {
        staffRepository.save(staff);
    }
    @Override
    public File getStaffImage(String userName) {
        File f = new File(imagePath + "/images/staff");
        try {
            File[] matchingFiles = f.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.startsWith(userName);
                }
            });
            return matchingFiles[0];
        } catch (Exception e) {
            File[] matchingFiles = f.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.startsWith("anon");
                }
            });
            return matchingFiles[0];
        }
    }

    public List<ClientRecord> getFutureRecords(List<ClientRecord> records) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = Calendar.getInstance().getTime();
        ArrayList<ClientRecord> futureDates = new ArrayList<>();
        for (ClientRecord clientRecord : records) {
            Date currentDate = dateFormat.parse(clientRecord.getDate());
            if (currentDate.after(today)) {
                futureDates.add(clientRecord);
            }
        }
        return futureDates;
    }
    @Override
    public Page<Staff> getAllStaff(StaffFilter filter, Pageable pageable) {
        var predicate = QPredicates.builder().add(filter.position(), staff.position::containsIgnoreCase)
                                             .add(filter.department(), staff.department::containsIgnoreCase).build();
        return staffRepository.findAll(predicate, pageable);
    }
}
